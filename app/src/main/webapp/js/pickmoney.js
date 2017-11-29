function PickMoney(){

    var me = this;
    me.items = ko.observableArray();
    me.load = function(){
        if(typeof me.dt != "object"){
            me.dt=$('#pickMoneyTables').DataTable({
                "processing": true,
                "serverSide": true,
                "order": [[ 12, "desc" ]],
                "ajax":'/stock/pick/allPickLogs',
                "aoColumns": [
                                 {"bSortable": false,"mData":"fund"},
                                 {"bSortable": false,"mData":"buyPriceOneA"},
                                 {"bSortable": false,"mData":"buyPriceOneB"},
                                 {"bSortable": false,"mData":"buyPriceTwoA"},
                                 {"bSortable": false,"mData":"buyPriceTwoB"},
                                 {"bSortable": false,"mData":"buyOneVolumeA"},
                                 {"bSortable": false,"mData":"buyOneVolumeB"},
                                 {"bSortable": false,"mData":"buyTwoVolumeA"},
                                 {"bSortable": false,"mData":"buyTwoVolumeB"},
                                 {"bSortable": false,"mData":"sellPriceOneA"},
                                 {"bSortable": false,"mData":"sellPriceOneB"},
                                 {"bSortable": false,"mData":"sellPriceTwoA"},
                                 {"bSortable": false,"mData":"sellPriceTwoB"},
                                 {"bSortable": false,"mData":"sellOneVolumeA"},
                                 {"bSortable": false,"mData":"sellOneVolumeB"},
                                 {"bSortable": false,"mData":"sellTwoVolumeA"},
                                 {"bSortable": false,"mData":"sellTwoVolumeB"},
                                 {"bSortable": false,"mData":"buyPremium"},
                                 {"bSortable": false,"mData":"sellPremium"},
                                 {"bSortable": false,"mData":"buySII"},
                                 {"bSortable": false,"mData":"sellSII"},
                                 {"bSortable": false,"mData":"inCome"},
                                 {"bSortable": false,"mData":"displayDate"}
                          ]

            });
            me.dt.columns(0).every( function () {
                var that = this;
                $( 'input', this.footer() ).on( 'keyup change', function () {
                    if ( that.search() !== this.value ) {
                        that.search( this.value ).draw();
                    }
                });
            });
        }

    };
}
