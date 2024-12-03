//구매목록/판매목록 내용물 보여주기
$(document).ready(function () {
    registerTabEvent('#tabEvent');

//구매/판매 목록 보기 탭
    function registerTabEvent(root) {
        $('div[name=tabTradeList]').hide();
        $('#OrList').hide();
        $.each($(root).find('.nav-link'), function (_i, _el) {
            $(_el).click(function () {
                $(root).find('.nav-link').removeClass('active');
                $(this).addClass('active');

                if ($(this).attr("tabindex") == 1) {
                    $('#SalList').show();
                    $('#OrList').hide();
                } else {
                    $('#SalList').hide();
                    $('#OrList').show();
                }
            });
        });
    }
});
