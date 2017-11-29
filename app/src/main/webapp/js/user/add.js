$(function(){
    var UserModel = function() {
        this.username = ko.observable("");
        this.password = ko.observable("");

        this.submit = function(){
            $.ajax({
                url:'/stock/user/saveOrUpdate',
                type:'POST',
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data:JSON.stringify({'username':this.username(),'password':this.password()}),
                success:function(){
                    location.href="/manage.html";
                }
            })
        };
    };

    ko.applyBindings(new UserModel());
});