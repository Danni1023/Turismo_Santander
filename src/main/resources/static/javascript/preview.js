function updateFileList() {
	const input = document.getElementById('imagenes');
	const fileList = document.getElementById('fileList');
	fileList.innerHTML = '';
	
	for (const file of input.files) {
		const li = document.createElement('li');
		li.textContent = file.name;
		li.classList.add('list-group-item');
		fileList.appendChild(li);
	}
}

document.addEventListener('DOMContentLoaded', function () {
	const imageInput = document.getElementById('imagenes');
	const previewContainer = document.createElement('div');
	previewContainer.classList.add('image-preview-container');
	
	imageInput.addEventListener('change', function () {
		previewContainer.innerHTML = '';
		
		Array.from(this.files).forEach(function (file) {
			const reader = new FileReader();
			reader.onload = function (event) {
				const img = document.createElement('img');
				img.src = event.target.result;
				img.classList.add('image-preview');
				img.style.marginRight = '10px';
				img.style.height = '150px';
				img.style.marginTop = '15px';
				img.style.marginBottom = '22px';
				previewContainer.appendChild(img);
			};
			reader.readAsDataURL(file);
		});
	});
	
	imageInput.parentNode.insertBefore(previewContainer, imageInput.nextSibling);
});
