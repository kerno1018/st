function Square(){

    var me = this;
    me.items = ko.observableArray();
    me.load = function(){
        if(typeof me.dt != "object"){
            me.dt = $('#squareTables').DataTable({
                "bPaginate": true,
                "bInfo" : false,
                "bAutoWidth" : true,
                "aoColumnDefs": [
                    { "sWidth": "11%"},
                    { "sWidth": "11%"},
                    { "sWidth": "11%"},
                    { "sWidth": "11%"},
                    { "sWidth": "11%"},
                    { "sWidth": "11%"},
                    { "sWidth": "11%"},
                    { "sWidth": "11%"},
                ]
            });
        }
        $.ajax({
            url:'/stock/log/getSquareLog',
            type:'get',
            success:function(response){
                var items = ko.observableArray();
                $.each(response,function(index,item){
                    items.push([
                        item.displayBuyFundNetValue,
                        item.realBuyFNV,
                        item.displaySellFundNetValue,
                        item.realSellFNV,
                        item.displayDealPriemum,
                        item.disaplyRealPremium,
                        item.displayIncome,
                        item.displaySellTime
                    ]);
                });
                me.dt.clear();
                me.dt.rows.add(items()).draw();
            }
        });
    };
}
