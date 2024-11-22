$(document).ready(function(){
    // 카테고리(동물, 여행, 인물, 음식) 전체 체크박스 선택시 개별 항목 체크 되도록 하는 jQuery
    $('#cateAllCheck').click(function() {
        let isChecked = $(this).is(':checked');
        console.log("cateAllCheck isChecked :" +isChecked);
        $('#filterCategory input[name=categroy]').prop('checked', isChecked);
    });

    // 회원등급(A, B, C, D, E) 전체 체크박스 선택시 개별 항목 체크 되도록 하는 jQuery
    $('#gradeAllCheck').click(function() {
        let isChecked = $(this).is(':checked');
        console.log("gradeAllCheck isChecked :" +isChecked);
        $('#filterCategory input[name=gradeCate]').prop('checked', isChecked);
    });


    // #1. 카테고리 개별 체크박스 선택시 key(예,동물(MAN01))와 value(true(선택)/false(비선택))로
    //     반복문 사용하여 catemap이라는 map에 저장. --> by 상욱
    $('#filterSaveEvent').click(function() {
        let catemap = {};
        let _cateEls = $('#filterCategory input[name=categroy]');
        $.each(_cateEls, function(_i, _el){
            let _check = $(_el).is(':checked');
            let _key = $(_el).val();
            catemap[_key] = _check;
        });
        console.log(catemap);

        // #2. cateCookie라는 이름의 쿠키 생성하고, catemap을 JSON 형식으로 변환하여
        //     쿠키 값으로 저장, 유효기간은 시험목적으로 0.1시간으로 설정 --> by Moon
        setCookie("cateCookie", JSON.stringify(catemap), 0.1);
    })


    // #1. 회원 등급 개별 체크박스 선택시 key(예,A(GRD01))와 value(true(선택)/false(비선택))로
    //     반복문 사용하여 grademap이라는 map에 저장. --> copy & modified by Moon
    $('#filterSaveEvent').click(function() {
        let grademap = {};
        let _gradeEls = $('#filterCategory input[name=gradeCate]');
        $.each(_gradeEls, function(_i, _el){
            let _check = $(_el).is(':checked');
            let _key = $(_el).val();
            grademap[_key] = _check;
        });
        console.log(grademap);

        // #2. gradeCookie라는 이름의 쿠키 생성하고, grademap을 JSON 형식으로 변환하여
        //     쿠키 값으로 저장, 유효기간은 시험목적으로 0.1시간으로 설정 --> by Moon
        setCookie("gradeCookie", JSON.stringify(grademap), 0.1);
    })


    // 페이지 새로고침 후에도 카테고리 체크 항목 유지하도록 하는 jQuery
    // 저장된 쿠키(JSON 형태)를 가져와서 parsing하여 내부변수 _json(map type)에 저장
    //  --> by Moon
    $('#cateFilter').click(function () {
        let cateMapCookie = getCookie("cateCookie");
        console.log(cateMapCookie);
        if (cateMapCookie != null) {
            const _json = JSON.parse(cateMapCookie);
            console.log(_json);

            // 체크 박스 쿠키에 저장된 값으로 복원
            if (_json.MAN01 == true) {
                $('#cateAnimalCheck').prop('checked', true);
            }
            if (_json.MAN02 == true) {
                $('#cateTourCheck').prop('checked', true);
            }
            if (_json.MAN03 == true) {
                $('#catePersonCheck').prop('checked', true);
            }
            if (_json.MAN04 == true) {
                $('#cateFoodCheck').prop('checked', true);
            }
            if (_json.cateAll == true) {
                $('#cateAllCheck').prop('checked', true);
            }
        }
    })


    // 페이지 새로고침 후에도 회원등급 체크 항목 유지하도록 하는 jQuery
    $('#cateFilter').click(function () {
        let gradeMapCookie = getCookie("gradeCookie");
        console.log(gradeMapCookie);
        if (gradeMapCookie != null) {
            const _parsedJson = JSON.parse(gradeMapCookie);
            console.log(_parsedJson);

            // 체크 박스 쿠키에 저장된 값으로 복원
            if (_parsedJson.GRD01 == true) {
                $('#gradeACheck').prop('checked', true);
            }
            if (_parsedJson.GRD02 == true) {
                $('#gradeBCheck').prop('checked', true);
            }
            if (_parsedJson.GRD03 == true) {
                $('#gradeCCheck').prop('checked', true);
            }
            if (_parsedJson.GRD04 == true) {
                $('#gradeDCheck').prop('checked', true);
            }
            if (_parsedJson.GRD05 == true) {
                $('#gradeECheck').prop('checked', true);
            }
            if (_parsedJson.gradeAll == true) {
                $('#gradeAllCheck').prop('checked', true);
            }
        }
    })





    // 쿠키 만들기 jQuery  --> by Moon
    let setCookie = function(name, value, exp) {
        var date = new Date();
        date.setTime(date.getTime() + exp*60*60*1000);
        document.cookie = name + '=' + value + ';expires=' + date.toUTCString() + ';path=/';
    };

    // 쿠키 가져오기 jQuery   --> by Moon
    let getCookie = function(name) {
        var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
        return value? value[2] : null;
    };

});

