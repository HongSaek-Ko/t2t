let currentPage = 1;
function moreProducts(){
    $.ajax({
        url: '/product/list/' + currentPage,
        method: 'GET',
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
                    $('#scroller').append(html);
            });
        }
    });
}

$(window).on('scroll', function (){
    if ($(window).scrollTop() + $(window).height() >= $(document).height()) {
        currentPage++;
        moreProducts();
    }
});

moreProducts();
