<#include "header.ftl">
<span id="searchContent" style="display: none"></span>
<div class="layui-carousel" id="test1" lay-filter="test1">
    <div carousel-item="" id="topList">

    </div>
</div>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>文章列表</legend>
</fieldset>

<div style="padding: 20px; background-color: #F2F2F2;  ">
    <div class="layui-row layui-col-space15 acsDiv" id="acs" style=" ">

    </div>
</div>
<script>
    $(function () {
        $("#index").addClass('layui-this');
        $.ajax({
            "type": 'get',
            "cache": false,
            "async": false,
            "url": "/acs/top",
            success: function (ret) {
                $.each(ret.data, function (idx, obj) {
                    $('#topList').append('<div>\n' +
                        '            <div class="layui-card">\n' +
                        '                <div class="layui-card-header"><a style="font: bold; cursor: pointer;" onclick="show(' + obj.contentId + ')">' + obj.articleName + '</a></div>\n' +
                        '                <div class="layui-card-body" style="height: 200px; overflow: hidden">\n' +
                        '                    ' + subContent(obj.htmlContent) +
                        '                    \n' +
                        '                </div>\n' +
                        '            </div>\n' +
                        '        </div>');
                });
            },
            error: function (err) {
                console.log('请求失败')
            }
        });
    });
    function subContent(o) {
        if(o.length > 100){
            return o.substring(0, 500);
        }else{
            return o.substring(0, o.length);
        }
    }
    function show(id) {
        window.location.href="/web/show/" + id;
    }
    layui.use(['carousel', 'flow', 'form'], function() {
        var carousel = layui.carousel
            , form = layui.form;
            flow = layui.flow;

        //常规轮播
        carousel.render({
            elem: '#test1'
            ,width: '100%'
            , arrow: 'always'
        });

        flow.load({
            elem: '#acs' //流加载容器
            ,scrollElem: '#acs' //滚动条所在元素，一般不用填，此处只是演示需要。
            ,done: function(page, next){ //执行下一页的回调
                loadAcs(page, next);
            }
        });

    });

    function loadAcs(page, next) {
        var searchText = $("#searchContent").html();
        if("" !== searchText){
            searchAcs(searchText, page, next);
        }else {
            acs(searchText, page, next);
        }
        $(".layui-flow-more").hide();
        //模拟数据插入
        // setTimeout(function(){
        //
        //     // for(var i = 0; i < 8; i++){
        //     //     lis.push('<li>'+ ( (page-1)*8 + i + 1 ) +'</li>')
        //     // }
        //     var lis = [];
        //     //执行下一页渲染，第二参数为：满足“加载更多”的条件，即后面仍有分页
        //     //pages为Ajax返回的总页数，只有当前页小于总页数的情况下，才会继续出现加载更多
        //     next(lis.join(''), page < 10); //假设总页数为 10
        // }, 500);

    }
    function search(e) {
        var evt = window.event || e;
        if (evt.keyCode == 13) {
            var searchText = $("#search").val();
            $("#searchContent").html(searchText);
            $('#acs').html('');
            $(document).unbind();
            flow.load({
                elem: '#acs' //流加载容器
                ,scrollElem: '#acs' //滚动条所在元素，一般不用填，此处只是演示需要。
                ,done: function(page, next){ //执行下一页的回调
                    loadAcs(page, next);
                }
            });

            // if("" !== $("#searchContent").html()) {
            //     searchAcs(searchText, 1);
            // }else{
            //     acs(searchText, 1);
            // }
        }
    }

    function searchAcs(searchText, page, next) {
        $.ajax({
            "type": 'get',
            "cache": false,
            "async": false,
            "url": "/es/page/search?searchText=" + searchText + "&pageNum=" + page,
            success: function (ret) {
                $.each(ret.data, function (idx, obj) {
                    $('#acs').append('<div class="layui-col-md6 codeDiv" >\n' +
                        '            <div class="layui-card">\n' +
                        '                <div class="layui-card-header"><a style="font: bold; cursor: pointer;" onclick="show(' + obj.contentId + ')">' + obj.articleName + '</a></div>\n' +
                        '                <div class="layui-card-body" style="height: 200px; overflow: hidden">\n' +
                        '                    ' + subContent(obj.htmlContent) +
                        '                    \n' +
                        '                </div>\n' +
                        '            </div>\n' +
                        '        </div>');
                });
                $(".codeDiv>code").remove();
                next('', page < 10); //假设总页数为 10
            },
            error: function (err) {
                console.log('请求失败')
            }
        });
    }

    function acs(searchText, page, next) {
        $.ajax({
            "type": 'get',
            "cache": false,
            "async": false,
            "url": "/es/page/search?searchText=" + "&pageNum=" + page,
            success: function (ret) {
                $.each(ret.data, function (idx, obj) {
                    $('#acs').append('<div class="layui-col-md6 codeDiv" >\n' +
                        '            <div class="layui-card">\n' +
                        '                <div class="layui-card-header"><a style="font: bold; cursor: pointer;" onclick="show(' + obj.contentId + ')">' + obj.articleName + '</a></div>\n' +
                        '                <div class="layui-card-body" style="height: 200px; overflow: hidden">\n' +
                        '                    ' + subContent(obj.htmlContent) +
                        '                    \n' +
                        '                </div>\n' +
                        '            </div>\n' +
                        '        </div>');
                });
                $(".codeDiv>code").remove();
                next('', page < 10); //假设总页数为 10
            },
            error: function (err) {
                console.log('请求失败')
            }
        });
    }
</script>
<#include "footer.ftl">
