document.addEventListener('DOMContentLoaded', () => {
	
	const leftArrowDestino = document.getElementById('leftArrow');
	const rightArrowDestino = document.getElementById('rightArrow');
	const destinosWrapper = document.querySelector('.destinos-wrapper');
	const destinosCards = document.querySelectorAll('.destinos-wrapper .card');
	
	const leftArrowHotel = document.getElementById('leftArrow1');
	const rightArrowHotel = document.getElementById('rightArrow1');
	const hotelesWrapper = document.querySelector('.hoteles-wrapper');
	const hotelesCards = document.querySelectorAll('.hoteles-wrapper .card');
	
	const leftArrowRestaurante = document.getElementById('leftArrow2');
	const rightArrowRestaurante = document.getElementById('rightArrow2');
	const restaurantesWrapper = document.querySelector('.restaurantes-wrapper');
	const restaurantesCards = document.querySelectorAll('.restaurantes-wrapper .card');
	
	const checkCardCount = (cards, leftArrow, rightArrow) => {
		if (cards.length > 4) {
			rightArrow.style.display = 'block';
			leftArrow.style.display = 'block';
		} else {
			rightArrow.style.display = 'none';
			leftArrow.style.display = 'none';
		}
	};
	
	checkCardCount(destinosCards, leftArrowDestino, rightArrowDestino);
	checkCardCount(hotelesCards, leftArrowHotel, rightArrowHotel);
	checkCardCount(restaurantesCards, leftArrowRestaurante, rightArrowRestaurante);
	
	const scrollWrapper = (wrapper, direction) => {
		wrapper.scrollBy({
			left: direction * 300,
			behavior: 'smooth'
		});
	};
	
	rightArrowDestino.addEventListener('click', () => scrollWrapper(destinosWrapper, 1));
	leftArrowDestino.addEventListener('click', () => scrollWrapper(destinosWrapper, -1));
	
	rightArrowHotel.addEventListener('click', () => scrollWrapper(hotelesWrapper, 1));
	leftArrowHotel.addEventListener('click', () => scrollWrapper(hotelesWrapper, -1));
	
	rightArrowRestaurante.addEventListener('click', () => scrollWrapper(restaurantesWrapper, 1));
	leftArrowRestaurante.addEventListener('click', () => scrollWrapper(restaurantesWrapper, -1));
});
