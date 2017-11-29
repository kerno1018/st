function ConfigInfo(){
    var me = this;
    me.premium=ko.observable();
    me.operateMoney=ko.observable();
    me.strategy=ko.observable();
    me.convertToBean = function(obj){
        me.premium(obj.premium);
        me.operateMoney(obj.operateMoney);
        me.strategy(obj.strategy);
    }
}

function ConfigModel(){
    var me = this;
    me.config=new ConfigInfo();
    me.init = function(){
        $.ajax({
                url:'/stock/admin/getPrepareData',
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                type:'get',
                cache:false,
                success:function(response){
                    me.config.convertToBean(response);
                }
        });
    };

    me.updateStrategy = function(){
        $.ajax({
            url:'/stock/admin/updateStrategy?strategy='+me.config.strategy(),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type:'post',
            success:function(){
                $.notify({
                    message: 'Operate Success.'
                },{
                    type: 'success'
                });
            }
        });
    };

    me.updatePremium = function(){
        $.ajax({
            url:'/stock/admin/updatePremium?premium='+me.config.premium(),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type:'post',
            success:function(){
                $.notify({
                    message: 'Operate Success.'
                },{
                    type: 'success'
                });
            }
        });
    };

    me.updateOperateMoney = function(){
        $.ajax({
            url:'/stock/admin/updateOperateMoney?operateMoney='+me.config.operateMoney(),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type:'post',
            success:function(){
                $.notify({
                    message: 'Operate Success.'
                },{
                    type: 'success'
                });
            }
        });
    }
}
