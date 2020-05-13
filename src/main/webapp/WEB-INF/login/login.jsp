<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="https://developers.kakao.com/sdk/js/kakao.min.js"></script>

<style>
	
	
	.loginWrapper{
		margin-top: 50px;
		margin-left:auto;
		margin-right:auto;
		max-width: 400px;
		height: 250px;
		border-radius:5px;
		text-align: center;
		line-height:1.8;
	}
	
	#kakao-login-btn{
		margin-top:20px;
    	border-top: 1px solid #DDDDDD99;
	}
	.itemListWrapper{
		width: 960px;
		height: 480px;
		margin: 5px auto;
		text-align: center;
	}
	.itemList{
		float:left;
		width: 306px;
		height: 400px;
		margin: 5px;
		border-radius: 5px;
		border : 2px solid #DDDDDD;
	}
	.imageArea{
		width: 100%;
		height: 200px;
		background: #EEEEEE;
		overflow:hidden;
	}
	
	.imageArea > img{
		width: 100%;
		height: 100%;
	}
	.reviewArea{
		width: 100%;
		height: 180px;
		text-align: left;
		padding : 4px;
	}
	.reviewTitle{
		width: 100%;
		height: 20px;
		text-align: left;
		padding : 4px;
	}
	
	.reviewArea > textarea {
		resize: none;
		width: 98%;
		height: 168px;
		background:#AAAA0011;
		overflow:hidden;
    	border: 0px !important;
	}
	.slide-child{
		transform: translateY(50px);
        opacity: 0;
        transition: all 1s;
    }
    .is-visible{
		transform: translateY(0px);
        opacity: 1;
    }
    .cover-form {
        width: 300px;
        height: 600px;
        background: white;
        position: fixed;
        z-index: 10;
        border-radius: 5px;
        padding-top: 10px;

    }
    .form-title {
        font-size: 25px;
        font-weight: bold;
        width: 100%;
        text-align: center;
        padding: 10px;

    }
    .form-desc{
        font-size: 14px;
        text-align: left;
        padding: 20px;
    }
    .input-title{
        width: 150px;
        height: 50px;
    }
    .input-content{
        width: 250px;
        height: 300px;

    }
    .write-btn {
        border: solid;


    }
    
   
</style>

<script type="text/javascript">

$(document).ready(function(){
    $('#cover-form').hide();
	<c:if test="${ user == null }" >
    $('#write-btn').hide();
	Kakao.init('e817b49592630e4585366f25b5a72957');
    // 카카오 로그인 버튼을 생성합니다.
    Kakao.Auth.createLoginButton({
      container: '#kakao-login-btn',
      success: function(authObj) {
        // alert(JSON.stringify(authObj));
       Kakao.API.request({url:'/v2/user/me',
    	   success:function (res){
    		   var id = res.id;
    		   var email = (res.kaccount_email ? res.kaccount_email : '');
    		   var nickname = (res.properties && res.properties.nickname ? res.properties.nickname : '');

    		   // alert(id);
    		   // alert(email);
    		   // alert(nickname);
    		   // nickname = '치킨';

    		   $("#logininfo").text(nickname);
    		   $.post("/kakaoLogin",
	   			   {id:id, email : email, nickname : nickname}
	   			 	, function (data){
	   			 		if(data == 1){
	   			 			alert("로그인이 완료 되었습니다.");
	   			 			$("#kakao-login-btn").hide();
	   			 			$('#write-btn').show();

	   			 		}
	   			 	}
    		   )
    	   },
    	   fail:function (error){

    	   }})
       
      },
      fail: function(err) {
         alert(JSON.stringify(err));
      }
    });
    </c:if>
    var slideAelements = $('.slide-child')
    
    
    function animateSlideA() {
      slideAelements.each(function (i) {
          setTimeout(function () {
              slideAelements.eq(i).addClass('is-visible');
          }, 300 * (i + 1));
      });
    }
    $('#write-btn').click(function (event){
        $('#cover-form').show();
    })

    $('#btnSubmit').click(function (event) {
        event.preventDefault();
        var form = $('#fileUploadForm')[0];
        var data = new FormData(form);

        $('#btnSubmit').prop('disabled', true);
        $.ajax({
            url: "/fileUpload",
            type: 'POST',
            enctype: 'multipart/form-data',
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                alert('complete');
                $('#btnSubmit').prop('disabled', false);
                $('#cover-form').hide();
                location.refresh();
            },
            error: function (error) {
                alert('error');
                $('#btnSubmit').prop('disabled', false);
            }
        })

    })
    $('#cancelBtn').click(function(){
        $('#cover-form').hide();
    })

    animateSlideA() ;
    
});
</script>

<html>
<title>오프라인 리뷰 웹테스트</title>
<body>
    <div class="loginWrapper">
        <div>
            <div class="slide-child">OffREV 는 Offline</div>
            <div class="slide-child">Review Flatform 의 약자로써</div>
            <div class="slide-child">오프라인 후기 정보들을</div>
            <div class="slide-child">모아모아 제공합니다.</div>
        </div>
        <c:if test="${ user == null }" >
        <div id="kakao-login-btn">

        </div>
        </c:if>
        <div id="cover-form" class="cover-form">
            <div class="form-title">리뷰 쓰기</div>
            <div class="form-desc">오프라인 행사 리뷰를 작성해 주세요</div>
            <form action="/fileUpload" method="POST" enctype="multipart/form-data" id="fileUploadForm">
                제목 <input type="text" name="title" class="input-title"><br>
                내용 <textarea name="content"  class="input-content"></textarea>
                <input type="file" name="mediaFile">
                <input type="submit" value="저장" id="btnSubmit">
                <input type="button" id="cancelBtn" value="취소">
            </form>
        </div>
        <div id="write-btn" class="write-btn">
            글쓰기
        </div>
    </div>

    <div class="itemListWrapper">
        <c:forEach var="item" items="${reviewList}">
            <div class="itemList slide-child">
               <div class="imageArea"><img src="<c:out value="${ item.s3ImageUrl }" />"/></div>
               <div class="reviewArea">
                  <div class="reviewTitle" ><c:out value="${ item.title }" /> </div>
                  <textarea readonly><c:out value="${ item.content }" /></textarea>
               </div>
            </div>
        </c:forEach>
    </div>
</body>
</html>

