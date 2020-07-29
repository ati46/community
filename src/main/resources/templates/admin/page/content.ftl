<div class="layuimini-container layuimini-page-anim">
    <div class="layuimini-main">
        <div class="layui-form" style="z-index: 100;">
                <div class="layui-inline">
                    <label class="layui-form-label">分组选择框</label>
                    <div class="layui-input-block">
                        <input type="text" id="title" name="title" lay-verify="title" autocomplete="off" placeholder="请输入标题" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">搜索选择框</label>
                    <div class="layui-input-inline">
                        <select name="modules" id="folders" lay-verify="required" lay-search="">
                            <option value="0">直接选择或搜索选择</option>
                        </select>
                    </div>
                </div>
            </div>
    </div>
    <link rel="stylesheet" href="editormd/css/editormd.min.css" />
    <!-- 页面解析markdown为HTML显示需要的css -->
    <link rel="stylesheet" href="editormd/css/editormd.preview.min.css" />
    <!-- 弹出框 -->
    <div id="myeditor" style="overflow: hidden">
        <!-- 富文本编辑器 -->
        <div id="text-editormd"></div>
    </div>

    <script src="js/jquery-3.1.1.min.js"></script>
    <script src="editormd/editormd.min.js"></script>

    <!-- 页面markdown解析成HTML需要的js -->
    <script src="editormd/lib/marked.min.js"></script>
    <script src="editormd/lib/prettify.min.js"></script>
    <script src="editormd/lib/raphael.min.js"></script>
    <script src="editormd/lib/underscore.min.js"></script>
    <script src="editormd/lib/sequence-diagram.min.js"></script>
    <script src="editormd/lib/flowchart.min.js"></script>
    <script src="editormd/lib/jquery.flowchart.min.js"></script>

</div>

<script type="text/javascript">
    var testEditor;
    $(function () {
        $.ajax({
            "type": 'get',
            "cache": false,
            "url": "/folder",
            success: function(ret){
                console.info(ret);
                console.info(ret.data.list);
                $.each(ret.data.list, function(idx,obj){
                    console.info(obj);
                    $('#folders').append('<option value="' + obj.id + '">' + obj.title + '</option>');
                });
                renderForm();
            },
            error: function (err){
                console.log('请求失败')
            }
        })
        testEditor = editormd("text-editormd", {
            width: "100%",
            height: $('#webContent').height() - 86,
            path : '/editormd/lib/',
            theme : "dark",
            previewTheme : "dark",
            editorTheme : "pastel-on-dark",
            //markdown : md,             // 初始化编辑区的内容
            codeFold : true,        //代码折叠功能
            syncScrolling : true, //同步滚动
            saveHTMLToTextarea : true,    // 保存 HTML 到 Textarea
            searchReplace : true,
            //watch : false,                // 关闭实时预览
            htmlDecode : "style,script,iframe|on*",            // 开启 HTML 标签解析，为了安全性，默认不开启
            //toolbar  : false,             //关闭工具栏
            //previewCodeHighlight : false, // 关闭预览 HTML 的代码块高亮，默认开启
            emoji : true,
            taskList : true,
            tocm : true,         // Using [TOCM]
            tex : true,                   // 开启科学公式TeX语言支持，默认关闭
            flowChart : true,             // 开启流程图支持，默认关闭
            sequenceDiagram : true,       // 开启时序/序列图支持，默认关闭,
            //dialogLockScreen : false,   // 设置弹出层对话框不锁屏，全局通用，默认为true
            //dialogShowMask : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
            //dialogDraggable : false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
            //dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为0.1
            //dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为#fff
            toolbarIcons : function() {
                // Or return editormd.toolbarModes[name]; // full, simple, mini
                // Using "||" set icons align right.
                return ["undo","redo","|","bold","del","italic","quote","ucwords","uppercase","lowercase","|","h1","h2","h3","h4","h5","h6","|","list-ul","list-ol","hr","|","link","reference-link","image","code","preformatted-text","code-block","table","datetime","emoji","html-entities","pagebreak","|","goto-line","watch","preview","fullscreen","clear","search","|","help","info","testIcon"]
            },
            toolbarIconsClass : {
                testIcon : "fa-gears"  // 指定一个FontAawsome的图标类
            },
            // 自定义工具栏按钮的事件处理
            toolbarHandlers : {
                /**
                 * @param {Object}      cm         CodeMirror对象
                 * @param {Object}      icon       图标按钮jQuery元素对象
                 * @param {Object}      cursor     CodeMirror的光标对象，可获取光标所在行和位置
                 * @param {String}      selection  编辑器选中的文本
                 */
                testIcon : function(cm, icon, cursor, selection) {

                    // //var cursor    = cm.getCursor();     //获取当前光标对象，同cursor参数
                    // //var selection = cm.getSelection();  //获取当前选中的文本，同selection参数
                    //
                    // // 替换选中文本，如果没有选中文本，则直接插入
                    // cm.replaceSelection("[" + selection + ":testIcon]");
                    //
                    // // 如果当前没有选中的文本，将光标移到要输入的位置
                    // if(selection === "") {
                    //     cm.setCursor(cursor.line, cursor.ch + 1);
                    // }
                    //
                    // // this == 当前editormd实例
                    // console.log("testIcon =>", this, cm, icon, cursor, selection);
                    var title = $("#title").val();
                    if(title === ""){
                        layer.alert('请填写文章名称', {
                            skin: 'layui-layer-molv' //样式类名
                            ,closeBtn: 0
                        });
                        return;
                    }
                    var folder = $("#folders").val();
                    if(folder === "0"){
                        layer.alert('请选择所属文件夹，若没有合适的请新建', {
                            skin: 'layui-layer-molv' //样式类名
                            ,closeBtn: 0
                        });
                        return;
                    }
                    var htmlContent = testEditor.getHTML();
                    var markContent = testEditor.getMarkdown();
                    var data = {
                        "title": title,
                        "folderId": folder,
                        "htmlContent": htmlContent,
                        "markContent": markContent
                    };
                    $.ajax({
                        "type": 'post',
                        "cache": false,
                        "url": "/article/content",
                        "data": JSON.stringify(data),
                        "contentType":'application/json;charset=utf-8',
                        success: function(ret){
                            layer.msg(ret.msg);
                            console.info(ret.code)
                            if(ret.code === 10000){
                                testEditor.setValue('');
                                $('#title').val('');
                            }
                        },
                        error: function (err){
                            console.log('请求失败')
                        }
                    })
                }
            },
            imageUpload : true,
            imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL : "./php/upload.php", // 文件上传路径，返回值为图片加载的路径
            onload : function() {
                // 加载后富文本编辑器成功后的回调
                console.log('onload', this);
                //this.fullscreen();
                //this.unwatch();
                //this.watch().fullscreen();

                //this.setMarkdown("#PHP");
                //this.width("100%");
                //this.height(480);
                //this.resize("100%", 640);

                // 异步请求md文件，用于编辑时的数据回显
                // $.get('test.md', function(md) {
                //     testEditor.setMarkdown(md);
                // });
            }
        });
    })
    $('#text-editormd').on('paste', function(e) {
        var _this       = this;
        var settings    = _this.settings;
        var classPrefix = _this.classPrefix;
        var items = (e.clipboardData || e.originalEvent.clipboardData).items;
        console.log(items[0], items[1]);

        //判断图片类型
        if (items && (items[0].type.indexOf('image') > -1 || items[1].type.indexOf('image') > -1)) {
            if (items[0].type.indexOf('image') > -1) {
                var file = items[0].getAsFile();
            } else {
                var file = items[1].getAsFile();
            }

            /*生成blob
            var blobImg = URL.createObjectURL(file);
            */

            /*base64
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function (e) {
                var base64Img = e.target.result //图片的base64
            }
            */

            // 创建FormData对象进行ajax上传
            var forms = new FormData(document.forms[0]); //Filename
            forms.append("classPrefix" + "image-file", file, "file_" + Date.parse(new Date()) + ".png"); // 文件
            console.log("classPrefix" + "image-file", file, "file_" + Date.parse(new Date()) + ".png")
            //调用imageDialog插件，弹出对话框
            _this.executePlugin("imageDialog", "image-dialog/image-dialog");

            _ajax(settings.imageUploadURL, forms, function (ret) {
                if (ret.success == 1) {
                    //数据格式可以自定义，但需要把图片地址写入到该节点里面
                    $("." + classPrefix + "image-dialog").find("input[data-url]").val(ret.url);
                    // cm.replaceSelection("![](" + ret.url  + ")");
                }
                console.log(ret.message);
            })
        }
        // ajax上传图片 可自行处理
        var _ajax = function(url, data, callback) {
            $.ajax({
                "type": 'post',
                "cache": false,
                "url": url,
                "data": data,
                "processData": false,
                "contentType": false,
                "mimeType": "multipart/form-data",
                success: function(ret){
                    callback(JSON.parse(ret));
                },
                error: function (err){
                    console.log('请求失败')
                }
            })
        }
    })
    layui.use(['layer', 'jquery'], function(){
        var layer = layui.layer
            , $= layui.jquery;


        $('#showEditor').on('click', function(){
            // 弹出框
            layer.open({
                type: 1
                ,content: $('#myeditor')
                ,btn: '关闭全部'
                ,btnAlign: 'c' //按钮居中
                ,shade: 0 //不显示遮罩
                ,area: ['1600px', '800px']
                ,yes: function(){
                    layer.closeAll();
                },
                success:function () {
                    testEditor = editormd("text-editormd", {
                        width: "90%",
                        height: 740,
                        path : 'editormd/lib/',
                        theme : "default",
                        previewTheme : "default",
                        editorTheme : "default",
                        //markdown : md,             // 初始化编辑区的内容
                        codeFold : true,
                        //syncScrolling : false,
                        saveHTMLToTextarea : true,    // 保存 HTML 到 Textarea
                        searchReplace : true,
                        //watch : false,                // 关闭实时预览
                        htmlDecode : "style,script,iframe|on*",            // 开启 HTML 标签解析，为了安全性，默认不开启
                        //toolbar  : false,             //关闭工具栏
                        //previewCodeHighlight : false, // 关闭预览 HTML 的代码块高亮，默认开启
                        emoji : true,
                        taskList : true,
                        tocm            : true,         // Using [TOCM]
                        tex : true,                   // 开启科学公式TeX语言支持，默认关闭
                        flowChart : true,             // 开启流程图支持，默认关闭
                        sequenceDiagram : true,       // 开启时序/序列图支持，默认关闭,
                        //dialogLockScreen : false,   // 设置弹出层对话框不锁屏，全局通用，默认为true
                        //dialogShowMask : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
                        //dialogDraggable : false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
                        //dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为0.1
                        //dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为#fff
                        imageUpload : true,
                        imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
                        imageUploadURL : "./php/upload.php", // 文件上传路径，返回值为图片加载的路径
                        onload : function() {
                            // 加载后富文本编辑器成功后的回调
                            console.log('onload', this);
                            //this.fullscreen();
                            //this.unwatch();
                            //this.watch().fullscreen();

                            //this.setMarkdown("#PHP");
                            //this.width("100%");
                            //this.height(480);
                            //this.resize("100%", 640);

                            // 异步请求md文件，用于编辑时的数据回显
                            // $.get('test.md', function(md) {
                            //     testEditor.setMarkdown(md);
                            // });
                        }
                    });
                }
            });
        });

        // 获取markdown源码
        $('#getMarkdownContent').on('click', function () {
            var mdContent = $('.editormd-markdown-textarea').val();
            console.log(mdContent);
            var content = testEditor.getMarkdown();
            console.log(content);
        });
        // 获取解析后的html
        $('#getHtmlContent').on('click', function () {
            var content = testEditor.getHTML();

            console.log(content);
        });

        // 页面解析markdown为html进行显示
        $('#showHTML').on('click', function () {
            // 模拟从数据库中取内容
            $.get('test.md', function(md) {
                // 给textarea赋值
                $('#content').val(md);
                // 解析
                editormd.markdownToHTML("markdownToHTML", {
                    htmlDecode      : "style,script,iframe",
                    emoji           : true,  // 解析表情
                    taskList        : true,  // 解析列表
                    tex             : true,  // 默认不解析
                    flowChart       : true,  // 默认不解析
                    sequenceDiagram : true  // 默认不解析
                });
            });
        });
    });
    function renderForm(){
        layui.use('form', function(){
        });
    }
</script>