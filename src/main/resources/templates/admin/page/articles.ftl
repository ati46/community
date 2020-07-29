<div class="layuimini-container layuimini-page-anim">
    <div class="layuimini-main">

        <fieldset class="table-search-fieldset">
            <legend>搜索信息</legend>
            <div style="margin: 10px 10px 10px 10px">
                <form class="layui-form layui-form-pane" action="">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">用户姓名</label>
                            <div class="layui-input-inline">
                                <input type="text" name="username" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">用户性别</label>
                            <div class="layui-input-inline">
                                <input type="text" name="sex" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">用户城市</label>
                            <div class="layui-input-inline">
                                <input type="text" name="city" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">用户职业</label>
                            <div class="layui-input-inline">
                                <input type="text" name="classify" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <button type="submit" class="layui-btn layui-btn-primary"  lay-submit lay-filter="data-search-btn"><i class="layui-icon"></i> 搜 索</button>
                        </div>
                    </div>
                </form>
            </div>
        </fieldset>

        <script type="text/html" id="toolbarDemo">
            <div class="layui-btn-container">
                <button class="layui-btn layui-btn-normal layui-btn-sm data-add-btn" lay-event="add"> 添加 </button>
                <button class="layui-btn layui-btn-sm layui-btn-danger data-delete-btn" lay-event="delete"> 删除 </button>
            </div>
        </script>

        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>

        <script type="text/html" id="currentTableBar">
            <a class="layui-btn layui-btn-normal layui-btn-xs data-count-edit" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-xs layui-btn-danger data-count-delete" lay-event="delete">删除</a>
        </script>

    </div>
</div>
<script type="text/html" id="toolbarDemo">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="add"  data-type="addRow">添加</button>
    </div>
</script>
<script>
    layui.use(['form', 'table','miniPage','element'], function () {
        var $ = layui.jquery,
            form = layui.form,
            table = layui.table,
            miniPage = layui.miniPage,
            layTableId = "menusTable";

        var tableIns = table.render({
            id: layTableId,
            elem: '#currentTableId',
            url: '/article',
            toolbar: 'default',
            defaultToolbar: ['filter', 'exports', 'print', {
                title: '提示',
                layEvent: 'LAYTABLE_TIPS',
                icon: 'layui-icon-tips'
            }],
            cols: [[
                {type: "checkbox", width: 50},
                {field: 'id', width: 80, title: 'ID', align:"center", sort: true},
                {field: 'title', width: 80, title: '标题', align:"center", edit: 'text'},
                {field: 'subTitle', minWidth: 80, title: '子标题',  align:"center", edit: 'text'},
                {field: 'folderId', minWidth: 80, title: '所属文件夹',  align:"center", edit: 'text'},
                {field: 'watchNum', minWidth: 80, title: '查看次数',  align:"center"},
                {field: 'commentNum', minWidth: 80, title: '评论次数',  align:"center"},
                {field: 'createTime', minWidth: 150, title: '创建时间', align:"center", sort: true, templet: "<div>{{layui.util.toDateString(d.createTime, 'yyyy年-MM月-dd日 HH:mm:ss')}}</div>"},
                {field: 'updateTime', title: '更新时间', minWidth: 150, align:"center", sort: true, templet: "<div>{{layui.util.toDateString(d.updateTime, 'yyyy年-MM月-dd日 HH:mm:ss')}}</div>"},
                {title: '操作', minWidth: 150, toolbar: '#currentTableBar', align: "center", align:"center"}
            ]],
            limits: [10, 15, 20, 25, 50, 100],
            limit: 15,
            page: true,
            skin: 'line',
            response: {
                statusCode: 10000 //重新规定成功的状态码为 200，table 组件默认为 0
            },
            parseData: function(res) { //将原始数据解析成 table 组件所规定的数据
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.data.count, //解析数据长度
                    "data": res.data.list //解析数据列表
                };
            }
        });

        //定义事件集合
        // var active = {
        //     addRow: function(){	//添加一行
        //         var oldData = table.cache[layTableId];
        //         console.log(oldData);
        //         var newRow = {id: 0, name: '请填写名称', url: '请填写路径', createTime: 0, updateTime: 0};
        //         oldData.push(newRow);
        //         console.info(oldData);
        //         tableIns.reload(layTableId, {
        //             data : oldData
        //         });
        //     },
        //     updateRow: function(obj){
        //         var oldData = table.cache[layTableId];
        //         console.log(oldData);
        //         for(var i=0, row; i < oldData.length; i++){
        //             row = oldData[i];
        //             if(row.tempId == obj.tempId){
        //                 $.extend(oldData[i], obj);
        //                 return;
        //             }
        //         }
        //         tableIns.reload({
        //             data : oldData
        //         });
        //     },
        //     removeEmptyTableCache: function(){
        //         var oldData = table.cache[layTableId];
        //         for(var i=0, row; i < oldData.length; i++){
        //             row = oldData[i];
        //             if(!row || !row.tempId){
        //                 oldData.splice(i, 1);    //删除一项
        //             }
        //             continue;
        //         }
        //         tableIns.reload({
        //             data : oldData
        //         });
        //     },
        //     save: function(){
        //         var oldData = table.cache[layTableId];
        //         console.log(oldData);
        //         for(var i=0, row; i < oldData.length; i++){
        //             row = oldData[i];
        //             if(!row.type){
        //                 layer.msg("检查每一行，请选择分类！", { icon: 5 }); //提示
        //                 return;
        //             }
        //         }
        //         document.getElementById("jsonResult").innerHTML = JSON.stringify(table.cache[layTableId], null, 2);	//使用JSON.stringify() 格式化输出JSON字符串
        //     }
        // }
        //
        // //激活事件
        // var activeByType = function (type, arg) {
        //     if(arguments.length === 2){
        //         active[type] ? active[type].call(this, arg) : '';
        //     }else{
        //         active[type] ? active[type].call(this) : '';
        //     }
        // }
        //
        // //注册按钮事件
        // $('.layui-btn[data-type]').on('click', function () {
        //     var type = $(this).data('type');
        //     activeByType(type);
        // });
        //
        // //监听select下拉选中事件
        // form.on('select(type)', function(data){
        //     var elem = data.elem; //得到select原始DOM对象
        //     $(elem).prev("a[lay-event='type']").trigger("click");
        // });
        //
        // //监听工具条
        // table.on('tool(dataTable)', function (obj) {
        //     var data = obj.data, event = obj.event, tr = obj.tr; //获得当前行 tr 的DOM对象;
        //     console.log(data);
        //     switch(event){
        //         case "type":
        //             //console.log(data);
        //             var select = tr.find("select[name='type']");
        //             if(select){
        //                 var selectedVal = select.val();
        //                 if(!selectedVal){
        //                     layer.tips("请选择一个分类", select.next('.layui-form-select'), { tips: [3, '#FF5722'] }); //吸附提示
        //                 }
        //                 console.log(selectedVal);
        //                 $.extend(obj.data, {'type': selectedVal});
        //                 activeByType('updateRow', obj.data);	//更新行记录对象
        //             }
        //             break;
        //         case "state":
        //             var stateVal = tr.find("input[name='state']").prop('checked') ? 1 : 0;
        //             $.extend(obj.data, {'state': stateVal})
        //             activeByType('updateRow', obj.data);	//更新行记录对象
        //             break;
        //         case "del":
        //             layer.confirm('真的删除行么？', function(index){
        //                 obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
        //                 layer.close(index);
        //                 activeByType('removeEmptyTableCache');
        //             });
        //             break;
        //     }
        // });

        //监听编辑
        table.on('edit(currentTableFilter)', function(obj){
            var value = obj.value //得到修改后的值
                ,data = obj.data //得到所在行所有键值
                ,field = obj.field; //得到字段
            console.info(data);
            layer.msg('[ID: '+ data.id +'] ' + field + ' 字段更改为：'+ value + '原始内容：' + data.url);
            layer.confirm('确认修改' +field + '的值为：' + value + '？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajax({
                    "type": 'put',
                    "cache": false,
                    "url": "/article",
                    "contentType": "application/json",
                    "data": JSON.stringify(data),
                    success: function(ret){
                        layer.msg("更新成功", {time: 1000});
                    },
                    error: function (err){
                        layer.msg('请求失败：' + err);
                    }
                })
            }, function(){
                layer.msg("撤回成功", {time: 1000});
                // layer.msg('也可以这样', {
                //     time: 20000, //20s后自动关闭
                //     btn: ['明白了', '知道了']
                // });
            });
        });

        // 监听搜索操作
        form.on('submit(data-search-btn)', function (data) {
            var result = JSON.stringify(data.field);
            layer.alert(result, {
                title: '最终的搜索信息'
            });

            //执行搜索重载
            table.reload('currentTableId', {
                page: {
                    curr: 1
                }
                , where: {
                    searchParams: result
                }
            }, 'data');

            return false;
        });

        /**
         * toolbar事件监听
         */
        table.on('toolbar(currentTableFilter)', function (obj) {
            // var checkStatus = table.checkStatus(obj.config.id);
            // layer.msg(obj.event);
            // switch(obj.event){
            //     case 'add':
            //         layer.msg('添加');
            //         activeByType('addRow', '');
            //         break;
            //     case 'delete':
            //         layer.msg('删除');
            //         break;
            //     case 'update':
            //         layer.msg('编辑');
            //         break;
            // };
            if (obj.event === 'add') {   // 监听添加操作
                var content = miniPage.getHrefContent('/admin/table/add');
                var openWH = miniPage.getOpenWidthHeight();

                var index = layer.open({
                    title: '添加用户',
                    type: 1,
                    shade: 0.2,
                    maxmin:true,
                    shadeClose: true,
                    area: [openWH[0] + 'px', openWH[1] + 'px'],
                    offset: [openWH[2] + 'px', openWH[3] + 'px'],
                    content: content,
                });
                $(window).on("resize", function () {
                    layer.full(index);
                });
            } else if (obj.event === 'delete') {  // 监听删除操作
                var checkStatus = table.checkStatus('currentTableId')
                    , data = checkStatus.data;
                layer.alert(JSON.stringify(data));
            }
        });

        //监听表格复选框选择
        table.on('checkbox(currentTableFilter)', function (obj) {
            console.log(obj)
        });

        table.on('tool(currentTableFilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'edit') {

                var content = miniPage.getHrefContent('/admin/table/edit');
                var openWH = miniPage.getOpenWidthHeight();

                var index = layer.open({
                    title: '编辑用户',
                    type: 1,
                    shade: 0.2,
                    maxmin:true,
                    shadeClose: true,
                    area: [openWH[0] + 'px', openWH[1] + 'px'],
                    offset: [openWH[2] + 'px', openWH[3] + 'px'],
                    content: content,
                });
                $(window).on("resize", function () {
                    layer.full(index);
                });
                return false;
            } else if (obj.event === 'delete') {
                layer.confirm('真的删除行么', function (index) {
                    obj.del();
                    layer.close(index);
                });
            }
        });

    });
</script>