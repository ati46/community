<#include "header.ftl">
<div class="layui-row">
    <div class="layui-col-xs4 layui-col-sm5 layui-col-md3"
         style="min-height: calc( 100vh - 61px); background-color: #393D49;">
        <div id="folderDiv" class="layui-col-xs6 layui-col-sm5 layui-col-md4"
             style="border-right: 1px solid #009688; height:calc( 100vh - 61px);" ondblclick="addFolder(event)">
            <ul class="layui-nav layui-nav-tree layui-inline" id="foldersUl" lay-filter="nav1"
                style="margin-right: 10px; margin-right: 10px; width: 100% !important;">
                <#list folders as item>
                    <li class="layui-nav-item ${(item_index == 0)?string("layui-this","")}" id="${item.id}"
                        av="${item.id}"><a href="javascript:;" onclick="findArticleList(${item.id})">${item.title}</a>
                    </li>
                </#list>
            </ul>
        </div>
        <div id="articleDiv" class="layui-col-xs6 layui-col-sm7 layui-col-md8"
             style="border-right: 1px solid #009688; height:calc( 100vh - 61px);" ondblclick="addArticle(event)">
            <ul id="articles" class="layui-nav layui-nav-tree layui-inline" lay-filter="nav2"
                style="margin-right: 10px; width: 100% !important;">

            </ul>
        </div>
    </div>
    <div class="layui-col-xs8 layui-col-sm7 layui-col-md9" style="height: calc(100vh - 61px); overflow: hidden;">
        <span id="contentId" style="display: none"></span>
        <span id="articleId" style="display: none"></span>
        <div id="bar" style="width: 100%; height: 30px; background-color: #393D49; border-bottom: 1px solid #009688;">
            <div style="float: left; height: 20px; margin-top: 5px; margin-left: 10px;">
                <i class="layui-icon layui-icon-note" style="font-size: 20px; color: whitesmoke;"></i>
                <div id="tag" style="margin-left: 30px;">
                </div>
            </div>
            <div id="tools" style="float: right; width: 20px; height: 20px; margin-top: 5px; margin-right: 10px;">
                <a id="edit" href="javascript:;" onclick="editmd()" title="编辑"><i class="layui-icon layui-icon-survey" style="font-size: 20px; color: whitesmoke;"></i></a>
            </div>
        </div>
        <#include "content.ftl">
    </div>
</div>

<script src="/js/jquery.cookie.js"></script>
<script src="/js/highlight.min.js"></script>
<script src="/js/uploadImg.js"></script>

<script>
    var element;
    layui.use('element', function () {
        element = layui.element;
        element.render('nav', 'nav1');
    });
    $(function () {
        $("#folder").addClass('layui-this');
        var folderId = $("#foldersUl li:first").attr("id");
        findArticleList(folderId);
    })

    function search(e) {
        var evt = window.event || e;
        if (evt.keyCode == 13) {
            var searchText = $("#search").val();
            $("#editormd-view").highlight(searchText);
        }
    }

    function findArticleList(folderId) {
        $.ajax({
            "type": 'get',
            "cache": false,
            "url": "/article/" + folderId,
            success: function (ret) {
                $('#articles').html('');
                $.each(ret.data, function (idx, obj) {
                    $('#articles').append('<li class="layui-nav-item ' + layThis(idx, obj.id) + '"><a href="javascript:;" onclick="findContent(' + obj.id + ')">' + obj.title + '</a></li>');
                });
                element.render('nav', 'nav2');
            },
            error: function (err) {
                console.log('请求失败')
            }
        })
    }

    function layThis(idx, id) {
        if (idx === 0) {
            findContent(id);
        }
        return idx === 0 ? "layui-this" : "";
    }

    function findContent(articleId) {
        $.ajax({
            "headers": {
                "Authorization": local_token
            },
            "type": 'get',
            "cache": false,
            "url": "/content/" + articleId,
            // beforeSend: function (XMLHttpRequest) {
            //     //HttpUtility.UrlEncode
            //     var sessionid = "Authorization";
            //     XMLHttpRequest.setRequestHeader(sessionid, local_token);
            // },
            success: function (ret) {
                $('#markDiv').empty();
                $('#markDiv').append('<div id="editormd-view" style="box-sizing: border-box;">\n' +
                    '        <textarea id="content-text" style="display:none;">\n' +
                    '\n' +
                    '        </textarea>\n' +
                    '    </div>');
                // var data = JSON.parse(ret);
                $('#contentId').html(ret.data.id);
                $('#articleId').html(articleId);
                showMarkDown(ret.data.markContent);
                showTag(ret.data.tag);

                $("#edit > i").removeClass("layui-icon-file");
                $("#edit").removeAttr("onclick");
                $("#edit > i").addClass("layui-icon-survey");
                $("#edit").attr("onclick","editmd();");
                $("#edit").attr("title","编辑");

                if (ret.data.status === 0) {
                    //showHtml(ret.data.htmlContent);
                    $("#tools").hide();
                } else {
                    $("#tools").show();
                }
            },
            error: function (err) {
                console.log('请求失败')
            }
        })
    }

    function getLoginStatus() {
        var result = false;
        $.ajax({
            "type": 'get',
            "cache": false,
            "async": false,
            "url": "/status",
            success: function (ret) {
                result = ret.data;
            },
            error: function (err) {
                layer.msg('请求失败');
                console.error(err);
                return false;
            }
        })
        return result;
    }

    function addFolder(e) {
        if (e.target.id === "folderDiv") {
            if (getLoginStatus()) {
                $('#foldersUl').append('<li class="layui-nav-item" id="newFolder"><input type="text" id="newFolderName" name="folderName" class="layui-input" onkeydown="inputFolderEnter(event)"></li>');
                //onkeydown="inputEnter(event)"   onblur="folerEvent()"
                $("#newFolderName").focus();
            } else {
                layer.msg("未登录");
            }
        }
    }

    $(window).scroll(function () {
        var t = $(document).scrollTop();
        //console.info(t);
    });

    function inputFolderEnter(e) {
        var evt = window.event || e;
        if (evt.keyCode == 13) {
            //回车后要干的业务代码
            folerEvent();
        } else if (evt.keyCode == 27) {
            //ESC

        }
    }

    function cleanFolder() {
        $("#newFolder").remove();
    }

    function folerEvent() {
        var title = $("#newFolderName").val();
        var menuId = $("#menus .layui-this").attr("av");
        layer.confirm('确认添加文件夹：' + title + '？', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            if (title === "" || title === null || title === undefined) {
                layer.alert('请输入文件夹名称', {
                    icon: 1,
                    skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
                })
                return;
            }
            $("#newFolder").html('');

            var data = {
                "menuId": menuId,
                "title": title
            }
            $.ajax({
                "type": 'post',
                "cache": false,
                "url": "/folder",
                "contentType": "application/json",
                "data": JSON.stringify(data),
                success: function (ret) {
                    $("#newFolder").append('<a href="javascript:;" onclick="findArticleList(' + ret.data.id + ')">' + ret.data.title + '</a>');
                    $("#newFolder").attr("av", ret.data.id);
                    $("#newFolder").removeAttr("id");
                    element.render('nav', 'nav1');
                    layer.msg('添加成功');
                },
                error: function (err) {
                    layer.msg('请求失败');
                    console.error(err);
                }
            })
        }, function () {
            $("#newFolder").remove();
            layer.msg('取消成功');
        });

    }

    function addArticle(e) {
        if (e.target.id === "articleDiv") {
            if (getLoginStatus()) {
                $('#articles').append('<li class="layui-nav-item" id="newArticle" ><input type="text" id="newArticleName" name="articleName" class="layui-input" onkeydown="inputArticleEnter(event)"></li>');
                //onkeydown="inputEnter(event)" onblur="cleanArticle()"
                $("#newArticleName").focus();
            } else {
                layer.msg("未登录");
            }
        }
    }

    function inputArticleEnter(e) {
        var evt = window.event || e;
        if (evt.keyCode == 13) {
            //回车后要干的业务代码
            articleEvent();
        } else if (evt.keyCode == 27) {
            //ESC

        }
    }

    function cleanArticle() {
        $("#newArticle").remove();
    }

    function articleEvent() {
        var title = $("#newArticleName").val();
        var folderId = $("#foldersUl .layui-this").attr("av");


        layer.confirm('确认添加文章：' + title + '？', {
            btn: ['确定', '取消'] //按钮
        }, function () {

            if (title === "" || title === null || title === undefined) {
                layer.alert('请输入文件夹名称', {
                    icon: 2,
                    skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
                })
                return;
            }
            $("#newArticle").html('');
            var data = {
                "folderId": folderId,
                "title": title
            }
            $.ajax({
                "type": 'post',
                "cache": false,
                "url": "/article",
                "contentType": "application/json",
                "data": JSON.stringify(data),
                success: function (ret) {
                    $('#newArticle').append('<a href="javascript:;" onclick="findContent(' + ret.data.id + ')">' + ret.data.title + '</a>');
                    $("#newArticle").removeAttr("id");
                    element.render('nav', 'nav2');
                    layer.msg('添加成功');

                },
                error: function (err) {
                    layer.msg('请求失败');
                    console.error(err);
                }
            })
        }, function () {
            $("#newArticle").remove();
            layer.msg('取消成功');
        });

    }

    function showHtml(data) {
        $('#editormd-view').html(data);
        editormd.markdownToHTML("editormd-view", {
            htmlDecode: "style,script,iframe",  // you can filter tags decode
            emoji: true,
            taskList: true,
            tex: true,  // 默认不解析
            flowChart: true,  // 默认不解析
            sequenceDiagram: true,  // 默认不解析,"fullscreen"
        });
    }

    function showMarkDown(markText) {
        testEditor = editormd("editormd-view", {
            width: "100%",
            height: $('#webContent').height(),
            path: '/editormd/lib/',
            theme: "dark",
            previewTheme: "dark",
            editorTheme: "pastel-on-dark",
            //markdown : md,             // 初始化编辑区的内容
            codeFold: true,        //代码折叠功能
            syncScrolling: true, //同步滚动
            saveHTMLToTextarea: true,    // 保存 HTML 到 Textarea
            searchReplace: true,
            //watch : false,                // 关闭实时预览
            htmlDecode: "style,script,iframe|on*",            // 开启 HTML 标签解析，为了安全性，默认不开启
            //toolbar  : false,             //关闭工具栏
            //previewCodeHighlight : false, // 关闭预览 HTML 的代码块高亮，默认开启
            emoji: true,
            taskList: true,
            //readOnly: true,
            tocm: true,         // Using [TOCM]
            tex: true,                   // 开启科学公式TeX语言支持，默认关闭
            flowChart: true,             // 开启流程图支持，默认关闭
            sequenceDiagram: true,       // 开启时序/序列图支持，默认关闭,
            //dialogLockScreen : false,   // 设置弹出层对话框不锁屏，全局通用，默认为true
            //dialogShowMask : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
            //dialogDraggable : false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
            //dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为0.1
            //dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为#fff
            toolbarIcons: function () {
                // Or return editormd.toolbarModes[name]; // full, simple, mini
                // Using "||" set icons align right.
                //return ["undo","redo","|","bold","del","italic","quote","ucwords","uppercase","lowercase","|","h1","h2","h3","h4","h5","h6","|","list-ul","list-ol","hr","|","link","reference-link","image","code","preformatted-text","code-block","table","datetime","emoji","html-entities","pagebreak","|","goto-line","watch","preview","fullscreen","clear","search","|","help","info","testIcon"]
                return ["undo", "redo", "|", "bold", "del", "italic", "quote", "ucwords", "uppercase", "lowercase", "|", "list-ul", "list-ol", "hr", "|", "link", "reference-link", "image", "code", "preformatted-text", "code-block", "table", "datetime", "emoji", "html-entities", "pagebreak", "|", "goto-line", "watch", "preview", "clear", "testIcon"]
            },
            toolbarIconsClass: {
                testIcon: "fa-gears"  // 指定一个FontAawsome的图标类
            },
            // 自定义工具栏按钮的事件处理
            toolbarHandlers: {
                /**
                 * @param {Object}      cm         CodeMirror对象
                 * @param {Object}      icon       图标按钮jQuery元素对象
                 * @param {Object}      cursor     CodeMirror的光标对象，可获取光标所在行和位置
                 * @param {String}      selection  编辑器选中的文本
                 */
                testIcon: function (cm, icon, cursor, selection) {
                    savemd();
                }
            },
            imageUpload: true,
            imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            imageUploadURL: "/upload", // 文件上传路径，返回值为图片加载的路径
            onload: function () {
                // 加载后富文本编辑器成功后的回调
                initPasteDragImg(this); //必须
                //this.fullscreen();
                //this.unwatch();
                //this.watch().fullscreen();

                //this.setMarkdown("#PHP");
                //this.width("100%");
                //this.height(480);
                //this.resize("100%", 640);
                testEditor.setMarkdown(markText);
                if (this.readOnly === true) {
                    //console.log("onloaded =>", this, this.id, this.settings);

                }

                this.previewing();
                // 异步请求md文件，用于编辑时的数据回显
                // $.get('test.md', function(md) {
                //     testEditor.setMarkdown(md);
                // });
            }
        });
    }

    function showTag(tags) {
        var arr = tags.split(',');
        $("#tag").html("");
        arr.forEach(function (item) {
            $("#tag").append("<span class='layui-badge'>" + item + "</span>")
        })
    }
    function editmd() {
        // var htmlContent = testEditor.getHTML();
        // var markContent = testEditor.getMarkdown();
        // $('#editormd-view').html('');
        // showMarkDown(markContent);
        // testEditor.readOnly = false;
        // testEditor.config("readOnly", false);
        $("#edit > i").removeClass("layui-icon-survey");
        $("#edit").removeAttr("onclick");
        $("#edit > i").addClass("layui-icon-file");
        $("#edit").attr("onclick","savemd();");
        $("#edit").attr("title","保存");
        //$("#edit").text('<i class="layui-icon layui-icon-file" style="font-size: 20px; color: #1E9FFF;"></i>');
        testEditor.previewing();
    }
    function savemd() {
        var htmlContent = testEditor.getHTML();
        var markContent = testEditor.getMarkdown();
        var id = $("#contentId").html();
        var articleId = $("#articleId").html();
        var data = {
            "id": id,
            "articleId": articleId,
            "htmlContent": htmlContent,
            "markContent": markContent
        };
        $.ajax({
            "type": 'put',
            "cache": false,
            "url": "/content/detail",
            "data": JSON.stringify(data),
            "contentType": 'application/json;charset=utf-8',
            success: function (ret) {
                console.info(ret);
                $('#contentId').html(ret.data.id);
                $('#articleId').html(ret.data.articleId);
                layer.msg(ret.msg);


                $("#edit > i").removeClass("layui-icon-file");
                $("#edit").removeAttr("onclick");
                $("#edit > i").addClass("layui-icon-survey");
                $("#edit").attr("onclick","editmd();");
                $("#edit").attr("title","编辑");
                testEditor.previewing();
            },
            error: function (err) {
                layer.msg('保存失败');
                console.error(err);
            }
        })
    }
</script>
<#include "footer.ftl">