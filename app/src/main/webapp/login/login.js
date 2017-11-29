$(function(){
//$("#manage").modal('toggle');
function User(){
    this.username=ko.observable();
    this.password=ko.observable();
    this.email=ko.observable();
    this.changePage = function(ss){
        if(ss=='register'){
            $("#register").show();
            $("#login").hide();
        }else{
            $("#register").hide();
            $("#login").show();
        }
        this.username('');
        this.password('');
        this.email('');
    };
    this.register = function(){

        $.ajax({
            url:'/stock/user/register',
            type:'POST',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data:JSON.stringify({'username':this.username(),'password':this.password(),'email':this.email()}),
            success:function(response){
                if(response.id == null){
                    alert('regist failed');
                }else{
                    location.href="/stock/manage.html"
                }
            }
        })
    };
    this.login = function(){
        $.ajax({
            url:'/stock/user/login',
            type:'POST',
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data:JSON.stringify({'username':this.username(),'password':this.password(),'email':this.email()}),
            success:function(response){
                if(response.id == null){
                    alert('login failed');
                }else{
                    location.href="/stock/manage.html"
                }
            }
        })
    };
}
ko.applyBindings(new User());
});