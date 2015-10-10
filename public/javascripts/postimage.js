(function() {
    $(function(){
        // ドラッグ＆ドロップエリアエリア作成
        Dropzone.options.filedropzone = {
            paramName: "file", // The name that will be used to transfer the file
            maxFilesize: 2, // MB
            dictDefaultMessage: "画像をこのエリアにドラッグ＆ドロップをしてください",
            accept: function(file, done) {
                done();
                $(".dz-details").remove();
                $(".dz-progress").remove();
                $(".dz-error-message").remove();
                $(".dz-success-mark").remove();
                $(".dz-error-mark").remove();
            }
        };
    });
}).call(this);
