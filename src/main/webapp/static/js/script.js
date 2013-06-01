var commission = 0;
function popupShow(el) {
    var url = $("#formLink").attr("href") + "&provider_id=" + el.attr("id");
    $.get(url, function(data) {
        if (!data.error) {
            commission = data.commission;
            $("#commission").text(commission);
            $("#provider_name").text(el.text());
            $(".currency").text(data.currency);
            $(".popup_wrap").show();
        }else{
            alert(data.error);
        }
    });
}
function popupHide() {
    $(".popup_wrap").hide();
    showError("");
    $(".resetable").val("");
    $(".hidable").css("visibility", "hidden");
}
function showError(message) {
    $("#error").text(message);
}
function pay() {
    var account = $.trim($("#account").val()),
            money = $.trim($("#money").val());
    if (account === "") {
        showError(errors[0]);
        return;
    }
    if (money === "") {
        showError(errors[1]);
        return;
    }
    popupHide();
}
$(function() {
    $(".top form select").change(function() {
        $(this).parent().submit();
    });
    $(".cancel").click(popupHide);
    $("ul.providers li").click(function() {
        popupShow($(this))
    });
    var codes = new Array(8, 190, 37, 39, 46);
    $("#money").keydown(function(key) {
        if ((key.keyCode < 48 || key.keyCode > 57) && $.inArray(key.keyCode, codes) === -1 || $(this).val().length === 9||
            key.keyCode===190&&$(this).val().indexOf('.')>-1) {
            return false;
        }        
    });
    $("#money").keyup(function(key) {
        if (key.keyCode >= 48 && key.keyCode <= 57 || key.keyCode === 8 || key.keyCode === 190 || key.keyCode === 46) {
            var tval = $(this).val();
            if (tval[0] === "0" && tval.length > 1) {
                $(this).val(tval.replace(new RegExp("^0*"), ""));
            }
            if (tval[0] === "."&&tval.length===1){
                $(this).val(tval.replace(new RegExp("\\."), ""));
            }
            var value = parseFloat($(this).val());
            var comSum = value * (commission / 100.0);
            if (!value) {
                $(".hidable").css("visibility", "hidden");
            } else {
                $(".hidable").css("visibility", "visible");
            }
            comSum=comSum.toFixed(2);
            $("#commission_sum").text(comSum);
            $("#pay_sum").text((value - comSum).toFixed(2));
        }
    });
    $(".pay").click(pay);
});