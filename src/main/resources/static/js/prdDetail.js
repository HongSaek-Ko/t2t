$(document).ready(function () {
    registerEvent(['#complaintEvent', '#modifyEvent', '#deleteEvent']);
});

function registerEvent(_target) {
    $.each(_target, function (_i, _el) {
        $(_el).on('click', function (e) {
            var _id = e.target.value;
            console.log(e);
            if (_id == 'complaintEvent') {
                $('#complaintModal').find('.modal-body').empty(); // 모달 새로 열 때마다 기존 것 비워줘야하므로...
                $('#complaintModal').find('.modal-body').append(createComplaintLayout()); // 모달에 이거 추가...
                $('#complaintModalLabel').text('신고 사유를 선택해주세요.');
                $('#complaintModal').modal('show');

            } else if (_id =='modifyEvent') {
                $('#modifyPrdModal').find('.modal-body').empty();
                $('#modifyPrdModal').find('.modal-body').append(createModifyLayout());
                $('#modifyPrdModalLabel').text('수정하려면 비밀번호를 입력해주세요.');
                $('#modifyPrdModal').modal('show');

            } else if (_id == 'deleteEvent') {
                $('#deletePrdModal').find('.modal-body').empty();
                $('#deletePrdModal').find('.modal-body').append(createDeleteLayout());
                $('#deleteModalLabel').text('삭제하려면 비밀번호를 입력해주세요.');
                $('#deletePrdModal').modal('show');
            }
        });
    });
}

function createComplaintLayout() {
    var complaintArr = [];
    complaintArr.push('<form id="complaintForm">') // todo: db(코드)에서 받아오고, 코드 개수만큼 div 반복, 위치는 legend 밑
    complaintArr.push('    <fieldset>')
    // complaintArr.push('        <legend>신고 사유를 선택해주세요</legend>')
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

function createModifyLayout() {
    var modifyArr = [];
    modifyArr.push('<form id="modifyPrdForm">')
    modifyArr.push('    <div class="form-floating mb-2">')
    modifyArr.push('        <input type="password" class="form-control" id="floatingModifyPassword" placeholder="Password">')
    modifyArr.push('            <label for="floatingModifyPassword">Password</label>')
    modifyArr.push('    </div>')
    modifyArr.push('    <div class="mb-5" style="display: flex; justify-content: center">')
    modifyArr.push('        <button type="submit" class="btn btn-danger mx-2" id="toModifyPrd" >확인</button>')
    modifyArr.push('    </div>')
    modifyArr.push('    <ul class="nav justify-content-center mb-2">')
    modifyArr.push('        <li class="nav-item">')
    modifyArr.push('            <a class="nav-link" href="#">비밀번호 찾기</a>')
    modifyArr.push('        </li>')
    modifyArr.push('    </ul>')
    modifyArr.push('</form>')
    return modifyArr.join('')
}

function createDeleteLayout() {
    var deleteArr = [];
    deleteArr.push('<form id="deletePrdForm">')
    deleteArr.push('    <div class="form-floating mb-2">')
    deleteArr.push('        <input type="password" class="form-control" id="floatingDeletePassword" placeholder="Password">')
    deleteArr.push('            <label for="floatingDeletePassword">Password</label>')
    deleteArr.push('    </div>')
    deleteArr.push('    <div class="mb-5" style="display: flex; justify-content: center">')
    deleteArr.push('        <button type="submit" class="btn btn-danger mx-2" id="deletePrdComplete" >삭제</button>')
    deleteArr.push('    </div>')
    deleteArr.push('    <ul class="nav justify-content-center mb-2">')
    deleteArr.push('        <li class="nav-item">')
    deleteArr.push('            <a class="nav-link" href="#">비밀번호 찾기</a>')
    deleteArr.push('        </li>')
    deleteArr.push('    </ul>')
    deleteArr.push('</form>')

    return deleteArr.join('');
}



/*
$(document).ready(function(){
    var nowpoint = $('input[name="nowPoint"]:checked').val();
    console.log(money);
    $('#rechargeEvent').on('click',function(){
        $.ajax({
            type: post,
            url : "/member/mypage/charge/point",
            data :{
                "addpoint" : nowpoint
            },
        });
    })
})
*/
