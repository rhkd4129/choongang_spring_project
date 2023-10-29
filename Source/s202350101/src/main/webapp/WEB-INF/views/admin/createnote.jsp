<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/header.jsp" %>	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<style text="text/css">

        #test {
            margin-top: 15%;
            margin-bottom: 15%;
        }
    
        #notebox {
            width: 40%;
            height: auto; /* 변경된 높이 값 */
            background-color: lightgray;
            display: flex;
            margin: auto;
            flex-direction: column;
            align-items: center;
        }
</style>

<script type="text/javascript">
	$(function() {


		$.ajax({
			url			: '../main_header',
			dataType 	: 'text',
			success		: function(data) {
				$('#header').html(data);
			}
		});
		
		$.ajax({
			url			: '../main_menu',
			dataType 	: 'text',
			success		: function(data) {
				$('#menubar').html(data);
			}
		});
	
		$.ajax({
			url			: '../main_footer',
			dataType 	: 'text',
			success		: function(data) {
				$('#footer').html(data);
			}
		});
	});
</script>
</head>

<body>

	<!-- HEADER -->
	<header id="header"></header>


	<!-- CONTENT -->
	<div class="container-fluid">
		<div class="row">

			<!-- 메뉴 -->
			<div id="menubar"
				class="menubar border-right col-md-3 col-lg-2 p-0 bg-body-tertiary">
			</div>

			<!-- 본문 -->
			<main id="center" class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
				<!------------------------------ //개발자 소스 입력 START ------------------------------->

    <div id="notebox">
        <form>
            <div id="top">
                <label for="to">누구에게</label><br>
                <select>
                    <option>사용자1</option>
                    <option>사용자2</option>
                    <option>사용자3</option>
                </select>
            </div>
            <p>메시지</p>
            <div id="bottom">
                <textarea rows="4" cols="40"></textarea>

            </div>
            <input type="submit" class="btn btn-primary" value="작성완료">
        </form>
    </div>
				<!------------------------------ //개발자 소스 입력 END ------------------------------->
			</main>

		</div>
	</div>

	<!-- FOOTER -->
	<footer class="footer py-2">
		<div id="footer" class="container"></div>
	</footer>


</body>

</html>

