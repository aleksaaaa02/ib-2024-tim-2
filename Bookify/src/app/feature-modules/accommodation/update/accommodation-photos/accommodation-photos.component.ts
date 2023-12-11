import { Component, EventEmitter, Output } from '@angular/core';
import { ImagesDTO } from '../../model/images';

@Component({
  selector: 'app-accommodation-photos',
  templateUrl: './accommodation-photos.component.html',
  styleUrl: './accommodation-photos.component.css'
})

export class AccommodationPhotosComponent {
  @Output() photosChanged = new EventEmitter<ImagesDTO[]>();

  selectedImagesObject: ImagesDTO[] = [];

  onFilesSelected(event: any): void {
    const files: FileList | null = event.target.files;

    if (files) {
      for (let i = 0; i < files.length; i++) {
        const reader = new FileReader();
        reader.onload = (e) => {
          if (e.target) {
            const imageDataURL = e.target.result as string;
            const imageDTO: ImagesDTO = {
              url: imageDataURL,
              file: files[i]
            }
            this.selectedImagesObject.push(imageDTO);
          }
        };
        reader.readAsDataURL(files[i]);
      }
    }

    this.photosChanged.emit(this.selectedImagesObject);
  }

  removeImage(image: ImagesDTO): void {
    this.selectedImagesObject = this.selectedImagesObject.filter(img => img !== image);
    this.photosChanged.emit(this.selectedImagesObject);
  }

}