let currentPage = 1; // 현재 페이지 번호; 무한 스크롤 로드 시마다 증가

moreProducts(); // 페이지 처음 로드 시(스크롤 안내려도) 즉시 실행

function moreProducts(){ // 추가 게시글 로드
    $.ajax({
        url: '/product/list/' + currentPage, // GetMapping에서 /list/{page}로 걸어놨던 거
        method: 'GET', // GetMapping.
        success: function(data) {
            data.forEach(function (product){
                const html = '<div class="card" th:each="product : ${productList}" >\n' +
                    '                        <a href="/product/'+ $('#prdid').data('prdId') +'" style="text-decoration: none; color: black">\n' +
                    '                            <img src="" class="bd-placeholder-img card-img-top thumbnail" width="100%" height="200"\n' +
                    '                                 xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Image cap"\n' +
                    '                                 preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title>\n' +
                    '                            </img>\n' +
                    '                            <div class="card-body" >\n' +
                    '                                <h5 class="card-title" >'+ $('#prdTitle').data('title') +'</h5>\n' +
                    '                                <p class="card-text">'+ $('#prdCont').data('cont') +'</p>\n' +
                    '                                <p class="card-text" ><small class="text-muted">'+ $('#prdReg').data('reg') +'</small></p>\n' +
                    '                            </div>\n' +
                    '                        </a>\n' +
                    '                    </div>';
                    $('#scroller').append(html); // html 결합 결과 추가
            });
        }
    });
}
$(window).on('scroll', function (){ // 스크롤 이벤트
    //           현재 스크롤 위치           화면의 높이            전체 페이지의 높이
    if ($(window).scrollTop() + $(window).height() >= $(document).height()) {  // 현재 위치+화면 높이가 전체보다 큼 = 페이지 맨 아래(에 도달함)
        currentPage++; // 조건문 통과 시; 페이지 번호, 로드마다 증가
        moreProducts(); // 조건문 통과 시; 함수 실행...
    }
});


