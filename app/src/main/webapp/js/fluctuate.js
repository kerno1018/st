function FluctuateInfo(){
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

function FluctuateModel(){
    var me = this;
    me.grid = new GridInfo();
    me.items = ko.observableArray();
    me.load = function(){
        if(typeof me.dt != "object"){
            me.dt = $('#fluctuateTables').DataTable({
                "processing": true,
                "serverSide": true,
                "order": [[ 5, "desc" ]],
                "ajax":'/stock/fluctuate/getAll',
                "aoColumns": [
                    {"bSortable": false,"mData":"code", "sWidth": "10%"},
                    {"mData":"increase", "sWidth": "10%","mRender": function(data, type, full) {
                        return "<span style='color:"+(data>0 ? 'red':'green')+"'>"+data+"%</span>";}
                    },
                    {"bSortable": false,"mData":"volume", "sWidth": "10%"},
                    {"bSortable": false,"mData":"yesterdayVolume", "sWidth": "10%"},
                    {"bSortable": false,"mData":"ratio", "sWidth": "10%","mRender": function(data, type, full) {
                        return "<span style='color:"+(data>0 ? 'red':'green')+"'>"+data+"%</span>";}},
                    {"mData":"showDate", "sWidth": "20%"}

                ]
            });
            $('#fluctuateTables tfoot th').each( function () {
                var title = $(this).text();
                if(title=='Code' || title=='Date'){
                    $(this).html( '<input type="text" style="width:100px;" placeholder="Search '+title+'" />' );

                }
            } );
            me.dt.columns().every( function () {
                var that = this;
                $( 'input', this.footer() ).on( 'keyup', function () {
                    that
                        .search( this.value )
                        .draw();
                } );
            });
        }
    };
}
