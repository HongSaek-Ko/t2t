$(document).ready(function () {
    registerEvent(['#rechargeEvent', '#exchageEvent']);
});

function registerEvent(_target) {
    $.each(_target, function(_i, _el){
        $(_el).on('click', function(e) {
            var _id = e.target.value;
            console.log(e);
            if(_id == 'rechargeEvent') {
                $('#commonModal').find('.modal-body').empty();
                $('#commonModal').find('.modal-body').append(createRechargeLayout());
                $('#commonModalLabel').text('충전');
                $('#commonModal').modal('show');
            } else if(_id == 'exchageEvent') {
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
    rechargeArr.push('    <input className="form-check-input" type="radio" name="flexRadioDefault" id="5000"/>');
    rechargeArr.push('    <label className="form-check-label" htmlFor="5000">');
    rechargeArr.push('        5000');
    rechargeArr.push('    </label>');
    rechargeArr.push('</div>');
    rechargeArr.push('<div className="form-check">');
    rechargeArr.push('   <input className="form-check-input" type="radio" name="flexRadioDefault" id="10000"');
    rechargeArr.push('           checked/>');
    rechargeArr.push('    <label className="form-check-label" htmlFor="10000">');
    rechargeArr.push('        10000');
    rechargeArr.push('    </label>');
    rechargeArr.push('</div>');
    rechargeArr.push('<div className="form-check">');
    rechargeArr.push('   <input className="form-check-input" type="radio" name="flexRadioDefault" id="20000"');
    rechargeArr.push('           checked/>');
    rechargeArr.push('    <label className="form-check-label" htmlFor="20000">');
    rechargeArr.push('        20000');
    rechargeArr.push('    </label>');
    rechargeArr.push('</div>');
    rechargeArr.push('<div className="form-check">');
    rechargeArr.push('   <input className="form-check-input" type="radio" name="flexRadioDefault" id="30000"');
    rechargeArr.push('           checked/>');
    rechargeArr.push('    <label className="form-check-label" htmlFor="30000">');
    rechargeArr.push('        30000');
    rechargeArr.push('    </label>');
    rechargeArr.push('</div>');

    return rechargeArr.join('');
}


function createExchangeLayout() {
    var rechargeArr = [];

    rechargeArr.push('<div className="form-check">');
    rechargeArr.push('    <input className="form-check-input" type="radio" name="flexRadioDefault" id="5000"/>');
    rechargeArr.push('    <label className="form-check-label" htmlFor="5000">');
    rechargeArr.push('        5000');
    rechargeArr.push('    </label>');
    rechargeArr.push('</div>');
    rechargeArr.push('<div className="form-check">');
    rechargeArr.push('   <input className="form-check-input" type="radio" name="flexRadioDefault" id="10000"');
    rechargeArr.push('           checked/>');
    rechargeArr.push('    <label className="form-check-label" htmlFor="10000">');
    rechargeArr.push('        10000');
    rechargeArr.push('    </label>');
    rechargeArr.push('</div>');
    rechargeArr.push('<div className="form-check">');
    rechargeArr.push('   <input className="form-check-input" type="radio" name="flexRadioDefault" id="20000"');
    rechargeArr.push('           checked/>');
    rechargeArr.push('    <label className="form-check-label" htmlFor="20000">');
    rechargeArr.push('        20000');
    rechargeArr.push('    </label>');
    rechargeArr.push('</div>');
    rechargeArr.push('<div className="form-check">');
    rechargeArr.push('   <input className="form-check-input" type="radio" name="flexRadioDefault" id="30000"');
    rechargeArr.push('           checked/>');
    rechargeArr.push('    <label className="form-check-label" htmlFor="30000">');
    rechargeArr.push('        30000');
    rechargeArr.push('    </label>');
    rechargeArr.push('</div>');

    return rechargeArr.join('');
}