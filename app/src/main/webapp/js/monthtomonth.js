function MonthInfo(){
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

function MonthModel(){
    var me = this;
    me.grid = new GridInfo();
    me.items = ko.observableArray();
    me.load = function(){
        if(typeof me.dt != "object"){
            me.dt = $('#monthTables').DataTable({
                "processing": true,
                "serverSide": true,
                "ajax":'/stock/month/getAll',
                "bPaginate": true,
                "order": [[ 8, "desc" ]],
                "bInfo" : false,
                "bAutoWidth" : true,
                "aoColumns": [
                    {"bSortable": false,"mData":"limitUp", "sWidth": "7%"},
                    {"bSortable": false,"mData":"ratioUp", "sWidth": "7%","mRender": function(data, type, full) {
                        return "<span style='color:"+(data>0 ? 'red':'green')+"'>"+data+"%</span>";}},
                    {"bSortable": false,"mData":"limitDown", "sWidth": "7%"},
                    {"bSortable": false,"mData":"ratioDown", "sWidth": "7%","mRender": function(data, type, full) {
                        return "<span style='color:"+(data>0 ? 'red':'green')+"'>"+data+"%</span>";}},
                    {"bSortable": false,"mData":"limitUp5", "sWidth": "10%"},
                    {"bSortable": false,"mData":"ratioUp5", "sWidth": "10%","mRender": function(data, type, full) {
                        return "<span style='color:"+(data>0 ? 'red':'green')+"'>"+data+"%</span>";}},
                    {"bSortable": false,"mData":"limitDown5", "sWidth": "10%"},
                    {"bSortable": false,"mData":"ratioDown5", "sWidth": "10%","mRender": function(data, type, full) {
                        return "<span style='color:"+(data>0 ? 'red':'green')+"'>"+data+"%</span>";}},
                    {"mData":"showDate", "sWidth": "20%"}

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
}
