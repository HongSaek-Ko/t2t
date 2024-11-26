// 우리가 HTML에서 정한 scroller라는 id를 가진 div에서 찾아 scroller 로 const 선언
const scroller = document.querySelector('.scroller');

function checkScroll() {
// ScrollY는 스크롤된 위치를 가져와서 담았다. 둘다 스크롤 위치를 뜻한다.
    const scrollY = window.scrollY || window.pageYOffset;
// 브라우저 창의 내부 높이를 나타낸다.
    const windowInnerHeight = window.innerHeight;
// scroller의 높이를 나타낸다.
    const contentHeight = container.offsetHeight;
// 둘의 합이 창 전체의 높이를 넘어버린다면 더이상 표시할 내용이 있어도 공간이 없게된다.
    if (scrollY + windowInnerHeight >= contentHeight) {
// 그때 fetchMoreData라는 function을 실행 시켜준다.
        fetchMoreData();
    }
}

// 이벤트발생을 대기하도록 설정했다. 이벤트이름은 scroll
window.addEventListener('scroll', checkSrcoll);

/*이미 앞에서 0번, 1번페이지를 불러왔으니 2번부터 불러와야겠다. */
let page=2;

function fetchMoreDate(){
    /* 위에서 만든 페이지에서 정보를 받아온다. */
    fetch('/more/${page}')
        /* 그 정보를 json으로 변환*/
        .then(response => response.json())
        /* 그 정보를 data로 다룬다. 고정이름 data*/
        .then(data =>{
            /* 그 정보가 존재하는지 정보의 길이로 검사한다 */
            if(data.length>0){
                /* 여러개의 content객체가 각자 들어있으므로 foreach로 꺼내자. */
                data.forEach(content =>{
                    /* div 태그를 card이름을 붙인다음에, 그 class명을 card로 지정했다. */
                    const card = documnet.createElement('div');
                    card.classList.add('card');

                    /* card에 넣을 것들도 똑같은 원리로 생성해서 준비해두자. */
                    const image = document.createElement('img');
                    image.classList.add('thumb');

                    const middle = document.createElement('div');
                    middle.classList.add('middle');

                    const bottom = document.createElement('div');
                    bottom.classList.add('bottom');

                    /* 이제 만들어둔 thumb, middle, bottom에 값을 넣자. */
                    image.setAttribute('src', content.image);
                    middle.textContent = content.title;
                    bottom.textContent = content.content;

                    /* 그다음으로 그것들을 카드에 모두 추가한다. */
                    card.appendChild(image);
                    card.appendChild(middle);
                    card.appendChild(bottom);

                    /* 마지막으로 container에 card를 추가 (container는 앞의 javascript에서 srcoller로 선언) */
                    scroller.appendChild(card);

                });
                /* 여기까지가 forEach 문이다. 이것을 반복 끝낸 후 page를 1 증가시켜 다음에 호출되면 그 다음페이지를 가져오게함*/
                page++;
            }
        })
        /* 에러가 발생했다면 어디서 에러가 발생하는지 알기 위해서 error메세지를 console에 띄우도록 설정함. */
        .catch(error => cosole.error('fetchMoreData Error', error))
}