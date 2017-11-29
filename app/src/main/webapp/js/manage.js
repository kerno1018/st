$(function(){
infuser.defaults.templateUrl = "templates";
    function ManageModel(){
        var me = this;
        me.account = new AccountModel();
        me.personalInfo = me.account.getAccount();
        me.logStatistic = new Log();
        me.dealLog = new DealLog();
        me.square = new Square();
        me.grid = new GridModel();
        me.fluctuate = new FluctuateModel();
        me.month = new MonthModel();
        me.autoOrder = new AutoOrderModel();
        me.config = new ConfigModel();
        me.pickmoney = new PickMoney();
        me.buttonStyle = function(){
            return me.personalInfo.enable()==true?"btn btn-success":"btn btn-warning";
        };

        me.showPersonalInfo = function(){
            me.account.init();
            $("#manage").modal('toggle');
        };

        me.showLogInfo = function(){
            me.logStatistic.load();
            $("#logStatistic").modal('toggle');
        };

        me.showDealLogInfo = function(){
            me.dealLog.load();
            $("#dealLog").modal('toggle');
        };

        me.showSquareInfo = function(){
            me.square.load();
            $("#square").modal('toggle');
        };

        me.showGridInfo = function(){
            me.grid.load();
            $("#grid").modal('toggle');
        };
        me.showFluctuateInfo = function(){
            me.fluctuate.load();
            $("#fluctuate").modal('toggle');
        };
        me.showMonthInfo = function(){
            me.month.load();
            $("#month").modal('toggle');
        };
        me.showAutoSellInfo = function(){
            me.autoOrder.load();
            $("#autoOrder").modal('toggle');
            $('#OperateTime').datetimepicker({
                minuteStep:1,
                format:"yyyy-mm-dd hh:ii:ss"
            });
        };
        me.showConfigInfo = function(){
            me.config.init();
            $("#config").modal('toggle');
        };
        me.showPickInfo = function(){
            me.pickmoney.load();
            $("#pickMoneyLog").modal('toggle');
            
        }
    }
    ko.applyBindings(new ManageModel());
});
