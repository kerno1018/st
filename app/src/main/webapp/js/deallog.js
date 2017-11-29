function DealLog(){

    var me = this;
    me.items = ko.observableArray();
    me.load = function(){
        if(typeof me.dt != "object"){
            me.dt = $('#dealLogTables').DataTable({
                    "bPaginate": true,
                    "bInfo" : false,
                    "bAutoWidth" : true,
                    "aoColumns": [
                        { "sWidth": "5%"},
                        { "sWidth": "5%"},
                        { "sWidth": "5%"},
                        { "sWidth": "5%"},
                        { "sWidth": "6%"},
                        { "sWidth": "6%"},
                        { "sWidth": "5%"},
                        { "sWidth": "6%"},
                        { "sWidth": "5%"},
                        { "sWidth": "6%"},
                        { "sWidth": "6%"},
                        { "sWidth": "6%"},
                        { "sWidth": "6%"},
                        { "sWidth": "6%"},
                        { "sWidth": "6%"},
                        { "sWidth": "6%"},
                        { "sWidth": "6%"},
                        { "sWidth": "5%"}
                    ],
                });
        }
        $.ajax({
            url:'/stock/log/getDealLog',
            type:'get',
            success:function(response){
                var items = ko.observableArray();
                $.each(response,function(index,item){
                    items.push([
                        item.sellStockA,
                        item.buyStockA,
                        item.displayDealBuyAvgPrice,
                        item.displayDealSellAvgPrice,
                        item.displayDealPriemum,
                        item.displayOwnSellAPrice,
                        item.displayOwnVolumeAMoney,
                        item.displayOwnSellBPrice,
                        item.displayOwnVolumeBMoney,
                        item.displaySellFundNetValue,
                        item.sellSII,
                        item.displayAdviseBuyAPrice,
                        item.displayAdviseVolumeAMoney,
                        item.displayAdviseBuyBPrice,
                        item.displayAdviseVolumeBMoney,
                        item.displayBuyFundNetValue,
                        item.buySII,
                        item.displayBuyTime


                    ]);
                });
                me.dt.clear();
                me.dt.rows.add(items()).draw();
            }
        });
    };
}
