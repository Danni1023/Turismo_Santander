document.addEventListener('DOMContentLoaded', function () {
	let currentImageIndex = 0;
	
	const imageContainer = document.getElementById('imagenesModal');
	const imagesString = imageContainer.getAttribute('data-images');
	
	const images = imagesString.split(',');
	
	function openModal(index) {
		currentImageIndex = index;
		document.getElementById('imageModal').style.display = "block";
		document.getElementById('modalImage').src = "data:image/jpeg;base64," + images[currentImageIndex];
	}
	
	function closeModal() {
		document.getElementById('imageModal').style.display = "none";
	}
	
	function changeImage(direction) {
		currentImageIndex += direction;
		if (currentImageIndex < 0) {
			currentImageIndex = images.length - 1;
		} else if (currentImageIndex >= images.length) {
			currentImageIndex = 0;
		}
		document.getElementById('modalImage').src = "data:image/jpeg;base64," + images[currentImageIndex];
	}
	
	window.openModal = openModal;
	window.closeModal = closeModal;
	window.changeImage = changeImage;
});
