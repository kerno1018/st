function Log(){

    var me = this;
    me.items = ko.observableArray();
    me.load = function(){
    if(typeof me.dt != "object"){
        me.dt=$('#logTables').DataTable({
            "processing": true,
            "serverSide": true,
            "order": [[ 12, "desc" ]],
            "ajax":'/stock/log/getAllLog',
            "aoColumns": [
                             {"bSortable": false,"mData":"stockA"},
                             {"bSortable": false,"mData":"changeToA"},
                             {"mData":"displayPremiumOne"},
                             {"bSortable": false,"mData":"displayPremiumTwo"},
                             {"bSortable": false,"mData":"displayOwnSellAPrice"},
                             {"bSortable": false,"mData":"displayOwnVolumeAMoney"},
                             {"bSortable": false,"mData":"displayOwnSellBPrice"},
                             {"bSortable": false,"mData":"displayOwnVolumeBMoney"},
                             {"bSortable": false,"mData":"displayAdviseBuyAPrice"},
                             {"bSortable": false,"mData":"displayAdviseVolumeAMoney"},
                             {"bSortable": false,"mData":"displayAdviseBuyBPrice"},
                             {"bSortable": false,"mData":"displayAdviseVolumeBMoney"},
                             {"mData":"displayDate"}
                      ]
            });
            $('#logTables tfoot th').each( function () {
                var title = $(this).text();
                if(title=='Date' || title=='Own' || title=='Advise'){
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
            // me.dt.columns(0).every( function () {
            //     var that = this;
            //     $( 'input', this.footer() ).on( 'keyup change', function () {
            //             if ( that.search() !== this.value ) {
            //                 that.search( this.value ).draw();
            //             }
            //     });
            // });
    }

//        $.ajax({
//            url:'/stock/log/getAllLog',
//            type:'get',
//            success:function(response){
//                var items = ko.observableArray();
//                $.each(response,function(index,item){
//                    items.push([
//
// .stockA
// .changeToA
// .displayPremiumOne
// .displayPremiumTwo
// .displayOwnSellAPrice
// .displayOwnVolumeAMoney
// .displayOwnSellBPrice
// .displayOwnVolumeBMoney
// .displayAdviseBuyAPrice
// .displayAdviseVolumeAMoney
// .displayAdviseBuyBPrice
// .displayAdviseVolumeBMoney
//  displayDate
// ]);
//                });
//                me.dt.clear();
//                me.dt.rows.add(items()).draw();;
//            }
//        });
    };
}
