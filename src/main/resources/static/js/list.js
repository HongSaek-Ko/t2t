let currentPage = 1; // 현재 페이지 번호; 무한 스크롤 로드 시마다 증가
$(document).ready(function () {
    moreProducts(); // 페이지 처음 로드 시(스크롤 안내려도) 즉시 실행
    $(window).on('scroll', function () { // 스크롤 이벤트
        //           현재 스크롤 위치           화면의 높이            전체 페이지의 높이
        if ($(window).scrollTop() + $(window).height() >= $(document).height()) {  // 현재 위치+화면 높이가 전체보다 큼 = 페이지 맨 아래(에 도달함)
            currentPage++; // 조건문 통과 시; 페이지 번호, 로드마다 증가
            moreProducts(); // 조건문 통과 시; 함수 실행...
        }
    });
});

function moreProducts() { // 추가 게시글 로드
    $.ajax({
        url: '/product/list/' + currentPage, // GetMapping에서 /list/{page}로 걸어놨던 거
        method: 'GET', // GetMapping.
        success: function (data) {
            let _arr0 =  [];
            let _arr1 =  [];
            let _arr2 =  [];

            $.each(data, function (_i, _el) {
                console.log(_el);
                console.log(_el.imgFile);

                const html =
                    `<div class="row">
                        <div class="col">
                            <a href="/product/${_el.prdId}">
                                <div class="card">
                                    <img
                                        src="${_el.imgFile != null ? '/product/image/' + _el.imgFile.fileNm : '/upload/profile/profile1.jpg'}"
                                        class="bd-placeholder-img card-img-top thumbnail" width="100%" height="200"/>
                                    <div class="card-body">
                                        <h5 class="card-title">${_el.title}</h5>\n
                                        <p class="card-text">${_el.cont}</p>\n
                                        <p class="card-text"><small class="text-muted">${_el.lastDt}</small></p>\n
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>`;
                switch(_i % 3) {
                    case 0 : _arr0.push(html); break;
                    case 1 : _arr1.push(html); break;
                    case 2 : _arr2.push(html); break;

                }
            });
            $('#scroller0').append(_arr0.join(''));
            $('#scroller1').append(_arr1.join(''));
            $('#scroller2').append(_arr2.join(''));
        }
    });
}

