$(function(){
    function StasticModel(){
        var me = this;
        me.items = ko.observableArray();
        if(typeof me.dt != "object"){
            me.dt = $('#stasticTables').DataTable({
                "processing": true,
                "serverSide": false,
                "ajax":'/stock/history/getAll',
                "bPaginate": true,
                "bInfo" : false,
                "bAutoWidth" : true,
                "aoColumns": [
                    {"mData":"code", "sWidth": "10%"},
                    {"mData":"rise20", "sWidth": "10%"},
                    {"mData":"avg20", "sWidth": "10%"},
                    {"mData":"rise30", "sWidth": "10%"},
                    {"mData":"avg30", "sWidth": "10%"},
                    {"mData":"rise60", "sWidth": "10%"},
                    {"mData":"avg60", "sWidth": "10%"},
                    {"mData":"rise120", "sWidth": "10%"},
                    {"mData":"avg120", "sWidth": "10%"},
                    {"mData":"now", "sWidth": "10%"}
                ]
            });
            // me.dt.columns(0).every( function () {
            //     var that = this;
            //     $( 'input[type="search"]', this.footer() ).on( 'keyup change', function () {
            //         if ( that.search() !== this.value ) {
            //             that.search( this.value ).draw();
            //         }
            //     });
            // });
        }
        // me.getAccount = function(){
        //     me.rollInfoId=setInterval(updateStatus,5000);
        //     return me.account;
        // };
    }
    ko.applyBindings(new StasticModel());
});