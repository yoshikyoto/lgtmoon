(function() {
    /** 画像一覧を表示するviewmodel */
    var vmImages
    /** 検索結果一覧を表示するviewmodel */
    var vmSearchResults

    /** ページがロードされた時に呼ばれる関数 */
    $(function() {
        initializeViewModel(); // view model の初期化
        // キーワード検索のハンドラを登録
        var form = $('#lgtmform');
        form.submit(function(event){
            event.preventDefault(); // 通常のpostは行わずAjax通信を行う
            search(); 
        });
        getRecent(); // 最近生成された画像の取得
    });

    /** view model の初期化をする */
    function initializeViewModel() {
        // 最近の画像の view model 初期化
        vmImages  = new Vue({
            el: '#images',
            data: {
                items: []
            }
        });
        // 検索結果の view model を初期化
        vmSearchResults = new Vue({
            el: "#search-results",
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

    /** 画像検索APIを叩き、結果を表示する */
    function search() {
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
        });
    }

    function postImageUrl(url) {
        var data = {'url': url};
        $.ajax({
            url: '/image.json',
            type: 'POST',
            dataType: 'json', // レスポンスのデータタイプ
            contentType: 'text/json',
            data: JSON.stringify(data),
            timeout: 10000
        }).done(function(json, status, xhr) {
            show("画像の生成が終わるまでしばらくお待ち下さい。");
        }).fail(function(xhr, status, error) {
            show(error);
        });
    }

    /** 最近の画像を取得して表示する */
    function getRecent() {
        $.ajax({
            url: '/recent.json',
            type: 'GET',
            cache: true,
            dataType: 'json'
        }).done(function(json, status, xhr) {
            vmImages.items = json.images;
        }).fail(function(error) {
            console.log(error);
        });
        setTimeout(getRecent, 30000);
    }

    /** @param message 検索フォームの横のmessageを表示する */
    function show(message) {
        $('#message').text(message);
    }
}).call(this);
