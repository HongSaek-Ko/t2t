$(document).ready(function() {
    // URL에서 prdId를 가져오기
    const prdId = window.location.pathname.split('/').pop();  // 마지막 부분이 prdId

    loadReplies(prdId);

    // 댓글 작성 폼 제출 시 처리
    $("#replyForm").on("submit", function(e) {
        e.preventDefault();  // 폼 제출 기본 동작을 막음

        const $input = $("textarea[name='content']");  // 댓글 내용 가져오기
        const content = $input.val();  // 댓글 내용

        if (content.trim() === "") {
            alert("댓글 내용을 작성해주세요.");
            return;
        }

        // 댓글 데이터를 서버에 POST 요청
        const replyData = {
            prdId: prdId,  // 게시글 ID
            rpyCont: content,  // 댓글 내용
            prtRpyId: null  // 부모 댓글 ID (대댓글을 작성하는 경우 이 값이 설정됨)
        };

        // AJAX POST 요청 (댓글 저장)
        $.ajax({
            url: "/replies/add",  // 댓글 저장 API 경로
            type: "POST",
            contentType: "application/json",  // JSON 형식으로 데이터를 전송
            data: JSON.stringify(replyData),  // 댓글 데이터를 JSON 형식으로 전송
            success: function() {
                // 댓글이 DB에 저장된 후 댓글 목록을 다시 불러옴
                loadReplies(prdId);  // 댓글 목록 다시 불러오기
                $input.val('');  // 입력창 초기화
            },
            error: function(xhr, status, error) {
                console.error("Error: " + error);
            }
        });
    });
});

// 댓글 목록을 서버에서 가져오는 함수
function loadReplies(prdId) {
    $.ajax({
        url: `/replies/${prdId}`,  // 댓글 목록 조회 API 경로
        type: "GET",
        success: function(replies) {
            const $commentList = $("#reply-list");
            $commentList.empty();  // 기존 댓글 목록 비우기

            // 댓글 목록을 화면에 추가
            replies.forEach(function(reply) {
                const row = createRow(reply.usrId, reply.rpyCont, reply.regDt);
                $commentList.append(row);
            });

            // 댓글 개수 업데이트 ('댓글쓰기' 안 span 태그 선택)
            $("h4 > span").text(`(${replies.length})`);
        },
        error: function(xhr, status, error) {
            console.error("Error: " + error);
        }
    });
}

// 댓글 HTML 구조를 생성하는 함수
function createRow(usrId, content, date) {
    const $ul = $("<ul>").addClass("comment-row");
    const $li1 = $("<li>").addClass("comment-id").text(usrId);
    const $li2 = $("<li>").addClass("comment-content").text(content);
    const $li3 = $("<li>").addClass("comment-date").text(date);

    $ul.append($li1, $li2, $li3);
    return $ul;
}
