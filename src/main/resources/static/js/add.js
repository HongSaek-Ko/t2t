$(document).ready(function (){
    toggleTextBox(['#trgtSalQty', '#islimit']);

    registerProductEvent();

    $(window).on('submit', function() {
        $( "#dialogContent" ).html("등록 완료!")
        $( "#dialog-confirm" ).dialog({
            resizable: false,
            height: "auto",
            width: 400,
            modal: true
        });
    });

});

function registerProductEvent () {
    $('#registerProductEvent').click(function() {
        let tags = $('#productForm tag');
        console.log(tags);
        console.log(tags.get(0));
        console.log(tags.get(0).value);

        let tags_attr = [];
        for(let key in tags) {
            console.log(key);
            tags_attr.push(tags[key].value);
        }
        console.log('tags_attr: ' + tags_attr);
        $('#tagId').val(tags_attr.join(','));
        console.log('tagId.val: ' + $('#tagId').val());

        $('#productForm').submit();
    });
}

$("#productForm").validate({
    rules: {
        title: {required: true, minlength: 2, maxlength: 20},
        cont: {required: true, maxlength: 200},
        cate: {required: true},
        price: {required: true},
        imgFile: {required: true},
        trgtSalQty: {required: true, minlength: 0}
    },
    messages: {
        title: "제목은 최소 2글자, 최대 20글자 입니다.",
        cont: "200글자 이내의 설명을 입력해주세요.",
        cate: "카테고리를 선택해주세요.",
        price: "가격을 설정해주세요.",
        imgFile: "상품 이미지를 올려주세요.",
        trgtSalQty: "판매 수량을 설정해주세요. (체크 해제 시 수량 제한(-), 설정 불필요)"
    },
    errorElement: "div",
    errorPlacement: function (error, element) {
        error.addClass("invalid-feedback");
        error.insertAfter(element);
    },
    highlight: function (element) {
        $(element).removeClass('is-valid').addClass('is-invalid');
    },
    unhighlight: function (element) {
        $(element).removeClass('is-invalid').addClass('is-valid');
    }
});



// 태그 넣는 코드 시작
var inputElements = document.querySelector('input[name=tagId]'),
    whitelist = []; // 기본은 빈배열

// initialize Tagify on the above input node reference
var tagify = new Tagify(inputElements, {
    enforceWhitelist: true,
    whitelist: inputElements.value.trim().split(/\s*,\s*/), // Array of values. stackoverflow.com/a/43375571/104380
    maxTags: 5
})



// "remove all tags" button event listener
document.querySelector('.tags--removeAllBtn')
    .addEventListener('click', tagify.removeAllTags.bind(tagify))

// Chainable event listeners
tagify.on('add', onAddTag)
    .on('remove', onRemoveTag)
    .on('input', onInput)
    .on('edit', onTagEdit)
    .on('invalid', onInvalidTag)
    .on('click', onTagClick)
    .on('focus', onTagifyFocusBlur)
    .on('blur', onTagifyFocusBlur)
    .on('dropdown:hide dropdown:show', e => console.log(e.type))
    .on('dropdown:select', onDropdownSelect)

var mockAjax = (function mockAjax(){
    var timeout;
    return function(duration){
        clearTimeout(timeout); // abort last request
        return new Promise(function(resolve, reject){
            timeout = setTimeout(resolve, duration || 700, whitelist)
        })
    }
})()

// on character(s) added/removed (user is typing/deleting)
// 태그 입력 시 목록 요청
function onInput(e){
    console.log("onInput: ", e)
    console.log("onInput detail: ", e.detail);
    console.log("onInput value: ", e.detail.value); // = 입력한 값(엔터는 안누름, 단순 입력)
    whitelist = []; // 현재 화이트리스트 초기화 (이전에 추가됐던 whitelist 제거)
    tagify.loading(true) // 불러오기 애니메이션
    $.ajax({
        url: '/tags/' + e.detail.value,
        datatype: 'JSON',
        method: 'GET',
        success: function (data) {
            console.log("data: ", data);
            $.each(data, function(index, el) {
                console.log("data.tagId: ", el);
                whitelist.push(el.tagId);
            });
        },
        error: function(error) {
            console.error('태그 목록 요청 실패:', error);
        }
    })
    // get new whitelist from a delayed mocked request (Promise)
    mockAjax()
        .then(function(result){
            tagify.settings.whitelist = result.concat(tagify.value) // add already-existing tags to the new whitelist array
            tagify
                .loading(false)
                // render the suggestions dropdown.
                .dropdown.show(e.detail.value);
        })
        .catch(err => tagify.dropdown.hide())
}

// tag added callback - 태그 추가하면 이후에 실행될 함수
function onAddTag(e){
    console.log("onAddTag: ", e.detail);
    console.log("original input value: ", inputElements.value)
    tagify.off('add', onAddTag) // exmaple of removing a custom Tagify event
}

// tag remvoed callback - 태그 삭제하면 이후에 실행될 함수
function onRemoveTag(e){
    console.log("onRemoveTag:", e.detail, "tagify instance value:", tagify.value)
}



function onTagEdit(e){
    console.log("onTagEdit: ", e.detail);
}

// invalid tag added callback
function onInvalidTag(e){
    console.log("onInvalidTag: ", e.detail);
}

// invalid tag added callback
function onTagClick(e){
    console.log(e.detail);
    console.log("onTagClick: ", e.detail);
}

function onTagifyFocusBlur(e){
    console.log(e.type, "event fired")
}

function onDropdownSelect(e){
    console.log("onDropdownSelect: ", e.detail)
}


function toggleTextBox(checkbox) {

    const textbox_elem = document.getElementById('trgtSalQty');
    const islimit_elem = document.getElementById('islimit');

    textbox_elem.disabled = !checkbox.checked; // 입력칸 활성화 = 체크박스 체크
    if (textbox_elem.disabled === false) { // 입력칸 비활성화가 아님 = 활성화됨
        islimit_elem.value = 'Y'; // 활성화될 경우 '제한여부' 값 'Y'
    } else { islimit_elem.value = 'N'; } // 아니라면 N

    if(textbox_elem.disabled) {
        textbox_elem.value = null;
    } else {
        textbox_elem.value = '';
        textbox_elem.focus();
    }
}




// 기존 default 프로필 사진을 선택한 사진으로 변경하는 함수 by Moon
function loadFile(input) {
    console.log(input.files[0]);
    let file = input.files[0];
    $('#defaultImg').attr("src", URL.createObjectURL(file));
}