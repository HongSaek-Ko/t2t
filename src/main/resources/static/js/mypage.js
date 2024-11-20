$(document).ready(function () {
    registerEvent(['#rechargeEvent', '#exchageEvent']);
});

function registerModalEvent() {
    $('.modal.fade #addMileBtn').on('click', function () {
        $.ajax(
    {
            url: "/member/mypage/charge",
            type: 'POST',
            data: {
                "point": $('.modal.fade.show .modal-body input[type=radio]:checked').val(),
            },
            datatype: 'json'}
        ).done(function (data, textStatus, xhr) {
            if(data.success == true)
                $('#mile').val(data.mile)
            else
                alert('충전 할수 없습니다.');
        });
    });
}

function registerEvent(_target) {
    $.each(_target, function (_i, _el) {
        $(_el).on('click', function (e) {
            var _id = e.target.value;
            console.log(e);
            if (_id == 'rechargeEvent') {
                $('#commonModal').find('.modal-body').empty();
                $('#commonModal').find('.modal-body').append(createRechargeLayout());
                $('#commonModalLabel').text('충전');
                $('#commonModal').modal('show');
                registerModalEvent();
            } else if (_id == 'exchageEvent') {
                $('#commonModal').find('.modal-body').empty();
                $('#commonModal').find('.modal-body').append(createExchangeLayout());
                $('#commonModal').modal('show');
            }
        });
    });
}

function createRechargeLayout() {
    var rechargeArr = [];

    rechargeArr.push('<div className="form-check">');
    rechargeArr.push('    <input className="form-check-input" type="radio" name="addMile" value="5000"/>');
    rechargeArr.push('    <label className="form-check-label" htmlFor="5000">');
    rechargeArr.push('        5000');
    rechargeArr.push('    </label>');
    rechargeArr.push('</div>');
    rechargeArr.push('<div className="form-check">');
    rechargeArr.push('   <input className="form-check-input" type="radio" name="addMile" value="10000"');
    rechargeArr.push('           checked/>');
    rechargeArr.push('    <label className="form-check-label" htmlFor="10000">');
    rechargeArr.push('        10000');
    rechargeArr.push('    </label>');
    rechargeArr.push('</div>');
    rechargeArr.push('<div className="form-check">');
    rechargeArr.push('   <input className="form-check-input" type="radio" name="addMile" value="20000"');
    rechargeArr.push('           checked/>');
    rechargeArr.push('    <label className="form-check-label" htmlFor="20000">');
    rechargeArr.push('        20000');
    rechargeArr.push('    </label>');
    rechargeArr.push('</div>');
    rechargeArr.push('<div className="form-check">');
    rechargeArr.push('   <input className="form-check-input" type="radio" name="addMile" value="30000"');
    rechargeArr.push('           checked/>');
    rechargeArr.push('    <label className="form-check-label" htmlFor="30000">');
    rechargeArr.push('        30000');
    rechargeArr.push('    </label>');
    rechargeArr.push('</div>');

    return rechargeArr.join('');
}


function createExchangeLayout() {
    rechargeArr = [];

    rechargeArr.push('<table className="table">');
    rechargeArr.push('  <tbody>');
    rechargeArr.push('  <tr>');
    rechargeArr.push('      <td colSpan="2">');
    rechargeArr.push('          <div className="input-group mb-3">');
    rechargeArr.push('              <span className="input-group-text" id="mile">보유 마일리지</span>');
    rechargeArr.push('              <input type="text" className="form-control" placeholder="Username"');
    rechargeArr.push('                     aria-label="Username" aria-describedby="mile" readOnly th:value="${myuser.mile.point}"/>');
    rechargeArr.push('          </div>');
    rechargeArr.push('      </td>');
    rechargeArr.push('  </tr>');
    rechargeArr.push('  <tr>')
    rechargeArr.push('      <td colSpan="2">')
    rechargeArr.push('          <div className="input-group mb-3">');
    rechargeArr.push('              <span className="input-group-text" id="grade">출금 가능한 마일리지</span>');
    rechargeArr.push('              <input type="text" className="form-control" aria-label="Username" aria-describedby="grade" readOnly');
    rechargeArr.push('                     th:value="${myuser.mile.point}"/>');
    rechargeArr.push('          </div>');
    rechargeArr.push('      </td>');
    rechargeArr.push('  </tr>');
    rechargeArr.push('  <tr>');
    rechargeArr.push('      <td colSpan="2">');
    rechargeArr.push('          <label htmlFor="bkname" className="form-label">bkName</label>');
    rechargeArr.push('          <input type="text" className="form-control" id="bkname" placeholder="은행명" readOnly');
    rechargeArr.push('                 th:value="${myuser.bankNm}"/>');
    rechargeArr.push('      </td>');
    rechargeArr.push('  </tr>');
    rechargeArr.push('  <tr>');
    rechargeArr.push('      <td colSpan="2">');
    rechargeArr.push('          <label htmlFor="bankAcnt" className="form-label">Bank name</label>');
    rechargeArr.push('          <input type="text" className="form-control" id="bankAcnt" placeholder="계좌번호"');
    rechargeArr.push('                 th:value="${myuser.bankAcnt}"/>');
    rechargeArr.push('      </td>');
    rechargeArr.push('  </tr>');
    rechargeArr.push('  <tr>');
    rechargeArr.push('      <td>');
    rechargeArr.push('          <button type="submit" className="btn btn-primary" id="minMileBtn">출금</button>');
    rechargeArr.push('      </td>');
    rechargeArr.push('  </tr>');


    return rechargeArr.join('');
}