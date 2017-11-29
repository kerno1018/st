function AccountInfo(){
    var me = this;
    me.id = ko.observable();
    me.username = ko.observable('');
    me.password = ko.observable();
    me.email = ko.observable();
    me.enable = ko.observable();
    me.accountId = ko.observable();
    me.cookie = ko.observable('');
    me.convertToBean = function (jsonObj){
        me.id(jsonObj.id);
        me.enable(jsonObj.enable);
        me.email(jsonObj.email);
        me.username(jsonObj.username);
        me.password(jsonObj.password);
        me.cookie(jsonObj.account.cookie);
        me.accountId(jsonObj.account.id);
    };
    me.toJson = function(){
        var jsonObj = {};
        jsonObj.id = me.id();
        jsonObj.username = me.username();
        jsonObj.enable=true;
        jsonObj.password = me.password();
        jsonObj.email = me.email();
        jsonObj.account={};
        jsonObj.account.id=me.accountId();
        jsonObj.account.cookie=me.cookie();
        return jsonObj;
    };
    return this;
}

function AccountModel(){
    var me = this;
    me.account = new AccountInfo();
    me.init = function(){
        $.ajax({
            url:'/stock/user/getUserInfo',
            type:'get',
            success:function(response){
                if(response.id == null){
                    if(me.rollInfoId != null){
                        //clearInterval(me.rollInfoId);
                        location.href="/index.html";
                    }
                }
                me.account.convertToBean(response);
            }
        });
    };
    var updateStatus = function(){
        $.ajax({
            url:'/stock/user/getUserInfo',
            type:'get',
            success:function(response){
                if(response &&  response != null){
                    me.account.enable(response.enable);
                }
            }
        });
    };
    me.getAccount = function(){
        me.rollInfoId=setInterval(updateStatus,5000);
        return me.account;
    };

    me.saveOrUpdate = function(){
        $.ajax({
            url:'/stock/user/saveOrUpdate',
            data:JSON.stringify(me.account.toJson()),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type:'post',
            success:function(response){
                console.log(response);
            }
        });
    };
}