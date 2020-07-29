<#include "header.ftl">
<link rel="stylesheet" href="/editormd/css/editormd.min.css" />
<!-- 页面解析markdown为HTML显示需要的css -->
<link rel="stylesheet" href="/editormd/css/editormd.preview.min.css" />
<div id="editormd-view">
    ${htmlContent}
</div>


<script src="/js/jquery-3.1.1.min.js"></script>
<script src="/js/highlight.min.js"></script>
<script src="/layui/layui.js"></script>
<script src="/editormd/editormd.min.js"></script>

<!-- 页面markdown解析成HTML需要的js -->
<script src="/editormd/lib/marked.min.js"></script>
<script src="/editormd/lib/prettify.min.js"></script>
<script src="/editormd/lib/raphael.min.js"></script>
<script src="/editormd/lib/underscore.min.js"></script>
<script src="/editormd/lib/sequence-diagram.min.js"></script>
<script src="/editormd/lib/flowchart.min.js"></script>
<script src="/editormd/lib/jquery.flowchart.min.js"></script>
<script>
    function search(e) {
        var evt = window.event || e;
        if (evt.keyCode == 13) {
            var searchText = $("#search").val();
            console.info(searchText);
            $("#editormd-view").highlight(searchText);
        }
    }
    editormd.markdownToHTML("editormd-view", {
        htmlDecode      : "style,script,iframe",  // you can filter tags decode
        emoji           : true,
        taskList        : true,
        tex             : true,  // 默认不解析
        flowChart       : true,  // 默认不解析
        sequenceDiagram : true,  // 默认不解析
    });
</script>
<#include "footer.ftl">