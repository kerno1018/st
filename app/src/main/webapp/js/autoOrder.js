function AutoOrderInfo(){
    var me = this;
    me.id = ko.observable();
    me.stock = ko.observable('');
    me.price = ko.observable();
    me.operateNum = ko.observable();
    me.operateTime = ko.observable();
    me.executeStatus = ko.observable();
    me.toJson = function(){
        var jsonObj = {};
        jsonObj.id = me.id();
        jsonObj.stock = me.stock();
        jsonObj.price = me.price();
        jsonObj.operateNum = me.operateNum();
        jsonObj.operateTime = me.operateTime();
        jsonObj.executeStatus=me.executeStatus();
        return jsonObj;
    };
    return this;
}

function AutoOrderModel(){
    var me = this;
    me.autoOrder = new AutoOrderInfo();
    me.items = ko.observableArray();
    me.load = function(){
        if(typeof me.dt != "object"){
            me.dt = $('#autoOrderTables').DataTable({
                "processing": true,
                "serverSide": true,
                "ajax":'/stock/autoOrder/getAutoOrderInfo',
                "bPaginate": true,
                "bInfo" : false,
                "bAutoWidth" : true,
                "aoColumns": [
                    {"mData":"id", "sWidth": "20%","mRender": function(data, type, full) {
                        return "<input type='checkbox' value="+data+" >"}
                    },
                    {"mData":"stock", "sWidth": "20%"},
                    {"mData":"price", "sWidth": "20%"},
                    {"mData":"operateNum", "sWidth": "20%"},
                    {"mData":"operateTime", "sWidth": "20%"},
                    {"mData":"executeStatus", "sWidth": "20%","mRender":function(data,type,full){
                        return data?"done":"preparing";
                    }}
                ],
            });
            me.dt.columns(1).every( function () {
                var that = this;
                $( 'input[type="search"]', this.footer() ).on( 'keyup change', function () {
                    if ( that.search() !== this.value ) {
                        that.search( this.value ).draw();
                    }
                });
            });
        }
    };
    me.deletes = function(){
        var rmIds = "";
        $("input:checked").each(function(i,item){
            rmIds +=item.value+",";
        });
        if(rmIds.length > 0){
            rmIds = rmIds.substr(0,rmIds.length-1);
        }
        $.ajax({
            url:'/stock/autoOrder/removeByIds?rmIds='+rmIds,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type:'post',
            success:function(response){
                me.dt.columns(0).search(1).draw();
            }
        });

    };

    me.saveOrUpdate = function(){
        $.ajax({
            url:'/stock/autoOrder/saveOrUpdate',
            data:JSON.stringify(me.autoOrder.toJson()),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type:'post',
            success:function(response){
                me.dt.columns(0).search(1).draw();
            }
        });
    };
}
