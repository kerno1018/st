function GridInfo(){
    var me = this;
    me.id = ko.observable();
    me.stock = ko.observable('');
    me.sellPrice = ko.observable();
    me.buyPrice = ko.observable();
    me.operateNum = ko.observable();

    //me.convertToBean = function (jsonObj){
    //    me.id(jsonObj.id);
    //    me.enable(jsonObj.enable);
    //    me.email(jsonObj.email);
    //    me.username(jsonObj.username);
    //    me.password(jsonObj.password);
    //    me.cookie(jsonObj.account.cookie);
    //    me.accountId(jsonObj.account.id);
    //};
    me.toJson = function(){
        var jsonObj = {};
        jsonObj.id = me.id();
        jsonObj.stock = me.stock();
        jsonObj.sellPrice=me.sellPrice();
        jsonObj.buyPrice = me.buyPrice();
        jsonObj.operateNum = me.operateNum();
        return jsonObj;
    };
    return this;
}

function GridModel(){
    var me = this;
    me.grid = new GridInfo();
    me.items = ko.observableArray();
    me.load = function(){
        if(typeof me.dt != "object"){
            me.dt = $('#gridTables').DataTable({
                "processing": true,
                "serverSide": true,
                "ajax":'/stock/grid/getGridInfo',
                "bPaginate": true,
                "bInfo" : false,
                "bAutoWidth" : true,
                "aoColumns": [
                    {"mData":"id", "sWidth": "20%","mRender": function(data, type, full) {
                        return "<input type='checkbox' value="+data+" >"}
                    },
                    {"mData":"stock", "sWidth": "20%"},
                    {"mData":"sellPrice", "sWidth": "20%"},
                    {"mData":"buyPrice", "sWidth": "20%"},
                    {"mData":"operateNum", "sWidth": "20%"},
                    {"mData":"enable", "sWidth": "20%","mRender":function(data,type,full){
                        return data?"done":"preparing";
                    }}

                ]
            });
            me.dt.columns(0).every( function () {
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
            url:'/stock/grid/removeByIds?rmIds='+rmIds,
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
            url:'/stock/grid/saveOrUpdate',
            data:JSON.stringify(me.grid.toJson()),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type:'post',
            success:function(response){
                me.dt.columns(0).search(1).draw();
            }
        });
    };
}
