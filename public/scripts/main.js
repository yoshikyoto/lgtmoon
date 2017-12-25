(function() {
    /** 画像一覧を表示するviewmodel */
    var vmImages
    /** ランダム画像一覧を表示するviewmodel */
    var vmRandomImages
    /** 検索結果一覧を表示するviewmodel */
    var vmSearchResults
    /** 画像の詳細情報を表示するviewmodel */
    var vmDetail

    /** ページがロードされた時に呼ばれる関数 */
    $(function() {
        initializeViewModel(); // view model の初期化
        // キーワード検索のハンドラを登録
        var form = $('#lgtmform');
        form.submit(function(event){
            event.preventDefault(); // 通常のpostは行わずAjax通信を行う
            submit();
        });

        initializeDropzone();

        getRecent(); // 最近生成された画像の取得
        getRandom(); // ランダム画像の取得
    });

    /** view model の初期化をする */
    function initializeViewModel() {
        // 最近の画像の view model 初期化
        vmImages  = new Vue({
            el: '#images',
            data: {
                items: []
            },
            methods: {
                /** 動画詳細モーダルを表示させる */
                detail: function(event) {
                    vmDetail.open(event);
                },
            }
        });
        // ランダム画像表示 view model 初期化
        vmRandomImages = new Vue({
            el: '#random-images',
            data: {
                items: []
            },
            methods: {
                detail: function(event) {
                    vmDetail.open(event);
                },
            },
        });
        // 画像詳細表示モーダルのview model を初期化する
        vmDetail = new Vue({
            el: '#detail',
            data: {
                url: '',
                seen: false,
            },
            methods: {
                /** 動画詳細モーダルを開く */
                open: function (event) {
                    var url = $(event.target).attr('src');
                    // オーバーレイとモーダルを表示させる
                    this.url = url;
                    this.seen = true
                },
                /** 動画詳細モーダルを閉じる */
                close: function(event) {
                    // オーバーレイとモーダルを消す
                    this.seen = false
                }
            }
        });
        // 検索結果の view model を初期化
        vmSearchResults = new Vue({
            el: '#search-results',
            data: {
                items: []
            },
            methods: {
                post: function(event) {
                    // post する画像のURL
                    var url = $(event.target).attr('src');
                    postImageUrl(url);
                }
            }
        });
    }

    /** ヘルプウィンドウを表示する */
    function openHelp() {
        $('.help').removeClass('hidden');
        $('.full-overlay').removeClass('hidden');
    }

    /** ヘルプウィンドウを表示する */
    function closeHelp() {
        $('.help').addClass('hidden');
        $('.full-overlay').addClass('hidden');
    }

    /**
     * 検索フォームに入力された文字列から、
     * 画像URLをpostまたは検索実行のいずれかを行う
     */
    function submit() {
        var urlRegexp = /^https?:\/\//;
        var keyword = $('input[name="keyword"]').val();
        if(keyword.match(urlRegexp) === null) {
            // マッチしない場合はURLではないのでsearch
            search();
        } else {
            // マッチする場合はpostImageUrl
            postImageUrl(keyword);
        }
    }

    /** 画像検索APIを叩き、結果を表示する */
    function search() {
        $('input[name="keyword"]').prop('disabled', true);
        $('.form-submit').prop('disabled', true);
        $('.result-section').removeClass('hidden');
        $('.result-section .overlay').removeClass('hidden');
        var keyword = $('input[name="keyword"]').val(); // 検索キーワードをフォームから取得
        $.ajax({
            url: '/search.json?keyword=' + keyword,
            type: 'GET',
            dataType: 'json', // レスポンスのデータタイプ
            timeout: 10000
        }).done(function(json, status, xhr) {
            show("検索結果から画像を選び、クリックしてください。");
            vmSearchResults.items = json.images;
        }).fail(function(xhr, status, error) {
            show(error);
        }).always(function() {
            // くるくるオーバーレイを無効化＆テキストエリア活性化。連打対策のため3秒待つ
            setTimeout(function() {
                $('.result-section .overlay').addClass('hidden');
                $('input[name="keyword"]').prop('disabled', false);
                $('.form-submit').prop('disabled', false);
            }, 3000);
        });
    }

    /** 画像をクリックした時に、画像生成のリクエストを送る */
    function postImageUrl(url) {
        $('input[name="keyword"]').prop('disabled', true);
        $('.form-submit').prop('disabled', true);
        $('.result-section .overlay').removeClass('hidden');
        var data = {'url': url};
        $.ajax({
            url: '/image.json',
            type: 'POST',
            dataType: 'json', // レスポンスのデータタイプ
            contentType: 'text/json',
            data: JSON.stringify(data),
            timeout: 10000
        }).done(function(json, status, xhr) {
            show("LGTM画像が生成されます。しばらくお待ち下さい。");
        }).fail(function(xhr, status, error) {
            show(error);
        }).always(function() {
            // くるくるオーバーレイを無効化するが、連打対策のため3秒待つ
            setTimeout(function() {
                $('input[name="keyword"]').prop('disabled', false);
                $('.form-submit').prop('disabled', false);
                $('.result-section .overlay').addClass('hidden');
            }, 3000);
        });
    }

    /** 最近の画像を取得して表示する */
    function getRecent() {
        $.ajax({
            url: '/api/v1/images/recent.json',
            type: 'GET',
            cache: true,
            dataType: 'json'
        }).done(function(json, status, xhr) {
            vmImages.items = json.images;
        }).fail(function(error) {
            console.log(error);
        });
        setTimeout(getRecent, 10000);
    }

    /** ランダムで画像を取得 */
    function getRandom() {
        $.ajax({
            url: '/api/v1/images/random.json',
            type: 'GET',
            cache: true,
            dataType: 'json'
        }).done(function(json, status, xhr) {
            vmRandomImages.items = json.images;
        }).fail(function(error) {
            console.log(error);
        });
    }


    /** @param message 検索フォームの横のmessageを表示する */
    function show(message) {
        $('#message').text(message);
    }

    function initializeDropzone() {
        $(".form-block").dropzone({
            url: "/image_binary.json",
            clickable: true,
            accept: function(file, done) {
                console.log("file uplaod accepted");
                show("ファイルアップロード中...")
                done()
            },
            success: function(file, message) {
                console.log("file uplaod succeeded");
                var json = JSON.parse(message)
                show(json.url);
            },
            error: function (file, message) {
                console.log("file uplaod error");
                show("サーバーエラー");
            },
            complete: function (file) {
                console.log("file upload processing complete");
            }
        });
    }

}).call(this);
