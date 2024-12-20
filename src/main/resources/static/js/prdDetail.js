var productId = $('#productId').data('productid');
$(document).ready(function () {
    $(window).on('submit', function (){
        window.location.href = '/'
    });
    registerEvent(['#complaintEvent', '#purchaseEvent', '#deleteEvent']);
    registerGoodEvent('#goodEvent');
    getPrdHashes();
    $(window).on('submit', function () {
        $("#dialogContent").html("요청 처리 완료!")
        $("#dialog-confirm").dialog({
            resizable: false,
            height: "auto",
            width: 400,
            modal: true,
            redirect: '/'
        });
    });
    $('#hide_effect').hide();
    $('#detail_effect').click(function () {
        $('#hide_effect').toggle(1000);
    });

    $('#productImg').watermark({
        text : $('#productId').data('prdusrid'),
        textSize : 20,
        outputType : 'webp',
        outputWidth: 500
    });
});

function getPrdHashes() {
    $.ajax({
        url: '/tags/prdHash/' + $('#productId').data('productid'),
        method: 'GET',
        success: function (data) {
            console.log("prdId: " + $('#productId').data('prdid'));
            console.log(data, "요청 성공!");
            console.log(data[0], "요청 성공!");
            $.each(data, function (_i, _el) {
                console.log(_i);
                console.log(_el.tagId);
                const html =
                    `<span class="badge text-bg-secondary"><h6>${_el.tagId}</h6></span>&ensp;`;
                $('#prdHash').append(html);
            });
        },
        error: function (error) {
            console.log("prdId: " + $('#productId').data('prdid'));
            console.log(error, "요청 실패.");
        }
    });
}


var clickgood = $('#goodEvent').data('clickgood');
function registerGoodEvent(registerGoodEvent) {
    $(registerGoodEvent).on('click', function () {
        // ajax 요청 1; 좋아요 정보 업데이트
        $.ajax({
            url: '/product/detail/good/' + productId,
            method: 'GET',
            success: function (data) {
                console.log("클릭 전: " + clickgood);
                if (clickgood === true) {
                    $('#heart-fill').removeClass().addClass('bi bi-heart');
                } else {
                    $('#heart-empty').removeClass().addClass('bi bi-heart-fill');
                }

                $.each(data, function (_i, _el) {
                    console.log(_el); // el: prdId
                    console.log(_i); // _i: good
                    console.log(data); // good:prdId
                });
                // ajax 요청 2; 좋아요 수 업데이트
                $.ajax({
                    url: '/product/goodCount/' + productId,
                    method: 'GET',
                    success: function (count) {
                        console.log("ajax post 요청 성공!");
                        console.log("count: " + count);
                        $("#goodCount").text(count);
                    },
                    error: function (error) {
                        console.log("ajax Get 요청 실패...");
                        console.log("error: " + error);
                    }
                });
            }
        });
    });
}

function registerEvent(_target) {
    $.each(_target, function (_i, _el) {
        $(_el).on('click', function (e) {
            var _id = e.target.value;
            console.log(productId); // prdId 가져오기, 다른 것들도 동일
            console.log(_target);
            console.log(_i);
            console.log(_el);
            console.log(e);
            console.log(e.target);
            if (_id === 'complaintEvent') {
                $('#complaintModal').find('.modal-body').empty(); // 모달 새로 열 때마다 기존 것 비워줘야하므로...
                $('#complaintModal').find('.modal-body').append(createComplaintLayout()); // 모달에 이거 추가...
                $('#complaintModalLabel').text('신고');
                $('#complaintModal').modal('show');

            } else if (_id === 'purchaseEvent') {
                $('#purchaseModal').find('.modal-body').empty();
                $('#purchaseModal').find('.modal-body').append(createPurchaseLayout());
                $('#purchaseModalLabel').text('T2T 구매');
                $('#purchaseModal').modal('show');

            } else if (_id === 'deleteEvent') {
                $('#deletePrdModal').find('.modal-body').empty();
                $('#deletePrdModal').find('.modal-body').append(createDeleteLayout());
                $('#deleteModalLabel').text('삭제');
                $('#deletePrdModal').modal('show');
            }
        });
    });
}

function createComplaintLayout() {
    var complaintArr = [];
    complaintArr.push('<form id="complaintForm">') // todo: db(코드)에서 받아오고, 코드 개수만큼 div 반복, 위치는 legend 밑
    complaintArr.push('    <fieldset>')
    complaintArr.push('        <legend>신고 사유를 선택해주세요.</legend>')
    complaintArr.push('        <div className="form-check">')
    complaintArr.push('            <input className="form-check-input" type="radio" name="flexRadioDefault" id="REP01" checked/>')
    complaintArr.push('            <label className="form-check-label" htmlFor="REP01"> 스팸 </label>')
    complaintArr.push('        </div>')
    complaintArr.push('        <div className="form-check">')
    complaintArr.push('            <input className="form-check-input" type="radio" name="flexRadioDefault" id="REP02" checked/>')
    complaintArr.push('            <label className="form-check-label" htmlFor="REP02"> 누드, 포르노 또는 성적 콘텐츠 </label>')
    complaintArr.push('        </div>')
    complaintArr.push('        <div className="form-check">')
    complaintArr.push('            <input className="form-check-input" type="radio" name="flexRadioDefault" id="REP03" checked/>')
    complaintArr.push('            <label className="form-check-label" htmlFor="REP03"> 자해 </label>')
    complaintArr.push('        </div>')
    complaintArr.push('        <div className="form-check">')
    complaintArr.push('            <input className="form-check-input" type="radio" name="flexRadioDefault" id="REP04" checked/>')
    complaintArr.push('            <label className="form-check-label" htmlFor="REP04"> 잘못된 정보 </label>')
    complaintArr.push('        </div>')
    complaintArr.push('        <div className="form-check">')
    complaintArr.push('            <input className="form-check-input" type="radio" name="flexRadioDefault" id="REP05" checked/>')
    complaintArr.push('            <label className="form-check-label" htmlFor="REP05"> 혐오 활동 </label>')
    complaintArr.push('        </div>')
    complaintArr.push('        <div className="form-check">')
    complaintArr.push('            <input className="form-check-input" type="radio" name="flexRadioDefault" id="REP06" checked/>')
    complaintArr.push('            <label className="form-check-label" htmlFor="REP06"> 위험 상품 </label>')
    complaintArr.push('        </div>')
    complaintArr.push('        <div className="form-check">')
    complaintArr.push('            <input className="form-check-input" type="radio" name="flexRadioDefault" id="REP07" checked/>')
    complaintArr.push('            <label className="form-check-label" htmlFor="REP07"> 모욕적인 내용 혹은 비난 </label>')
    complaintArr.push('        </div>')
    complaintArr.push('        <div className="form-check">')
    complaintArr.push('            <input className="form-check-input" type="radio" name="flexRadioDefault" id="REP08" checked/>')
    complaintArr.push('            <label className="form-check-label" htmlFor="REP08"> 노골적인 폭력 묘사 </label>')
    complaintArr.push('        </div>')
    complaintArr.push('        <div className="form-check">')
    complaintArr.push('            <input className="form-check-input" type="radio" name="flexRadioDefault" id="REP09" checked/>')
    complaintArr.push('            <label className="form-check-label" htmlFor="REP09"> 개인정보 침해 </label>')
    complaintArr.push('        </div>')
    complaintArr.push('        <div className="form-check">')
    complaintArr.push('            <input className="form-check-input" type="radio" name="flexRadioDefault" id="REP10" checked/>')
    complaintArr.push('            <label className="form-check-label" htmlFor="REP10"> 나의 지적 재산임 </label>')
    complaintArr.push('        </div>')
    complaintArr.push('        <div className="form-check">')
    complaintArr.push('            <input className="form-check-input" type="radio" name="flexRadioDefault" id="REP11" checked/>')
    complaintArr.push('            <label className="form-check-label" htmlFor="REP11"> 불법 촬영 콘텐츠 </label>')
    complaintArr.push('        </div>')
    complaintArr.push('        <hr/>')
    complaintArr.push('        <div className="mb-5" style="display: flex; justify-content: center">')
    complaintArr.push('            <button type="submit" class="btn btn-danger" className="btn btn-danger mx-2" id="complaintComplete">신고 완료</button>')
    complaintArr.push('        </div>')
    complaintArr.push('    </fieldset>')
    complaintArr.push('</form>')

    return complaintArr.join('');
}

function createDeleteLayout() {
    var deleteArr = [];
    deleteArr.push('<form id="deletePrdForm" action="/product/' + productId + '/delete" method="post">') // <...action="/product/prdId/delete"...>
    deleteArr.push('    <div class="mb-3" style="display: flex; justify-content: center">')
    deleteArr.push('        <h4> 정말 삭제하시겠습니까? </h4>')
    deleteArr.push('    </div>')
    deleteArr.push('    <div class="mb-2" style="display: flex; justify-content: center">')
    deleteArr.push('        <button type="submit" class="btn btn-danger mx-2" id="deletePrdComplete" >삭제</button>')
    deleteArr.push('    </div>')
    deleteArr.push('</form>')
    return deleteArr.join('');
}

function createPurchaseLayout() {
    var purchaseArr = [];
    purchaseArr.push('<form id="purchaseForm" action="/product/' + productId + '/purchase" method="post">') // <...action="/product/prdId/delete"...>
    purchaseArr.push('    <div class="mb-3" style="display: flex; justify-content: center">')
    purchaseArr.push('        <h4> 이 상품을 구매하시겠습니까? </h4>')
    purchaseArr.push('    </div>')
    purchaseArr.push('    <div class="mb-2" style="display: flex; justify-content: center">')
    purchaseArr.push('        <button type="submit" class="btn btn-danger mx-2" id="purchaseComplete" value="purchaseComplete" onclick="/">구매</button>')
    purchaseArr.push('    </div>')
    purchaseArr.push('</form>')
    return purchaseArr.join('');
}



