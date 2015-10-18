(function() {
    // 画像一覧を表示するviewmodel
    var vm

    // onload method
    $(function() {
        // initalize viewmodel
        vm  = new Vue({
            el: '#images',
            data: {
                items: []
            }
        });
        // post keyword
        var form = $('#lgtmform');
        form.submit(function(event) {
            event.preventDefault();
            var keyword = $('input[name="keyword"]').val();
            var data = { "keyword" : keyword };
            console.log(JSON.stringify(data));
            $.ajax({
                url: '/keyword.json',
                type: 'POST',
                dataType: 'json',
                contentType: 'text/json',
                data: JSON.stringify(data),
                timeout: 10000,
            }).done(function(json, status, xhr) {
                var url = json.url;
                show(url);
            }).fail(function(xhr, status, error) {
                show(error)
            });
        });
        // get lgtm images
        getRecent();
    });

    /**
     * 最近の画像を取得して表示する
     */
    function getRecent() {
        $.ajax({
            url: '/recent.json',
            type: 'GET',
            cache: false,
            dataType: 'json'
        }).done(function(json, status, xhr) {
            console.log('get');
            vm.items = json.images;
        }).fail(function(error) {
            console.log(error);
        });
        setTimeout(getRecent, 30000);
    }

    function show(message) {
        $('#message').text(message);
    }
}).call(this);
