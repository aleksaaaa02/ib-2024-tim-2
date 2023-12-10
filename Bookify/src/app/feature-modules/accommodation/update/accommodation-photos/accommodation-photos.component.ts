import { Component, EventEmitter, Output } from '@angular/core';
import { File } from 'buffer';

@Component({
  selector: 'app-accommodation-photos',
  templateUrl: './accommodation-photos.component.html',
  styleUrl: './accommodation-photos.component.css'
})
export class AccommodationPhotosComponent {
  @Output() photosChanged = new EventEmitter<string[]>();

  selectedImages: string[] = [];

  onFilesSelected(event: any): void {
    const files: FileList | null = event.target.files;
  
    if (files) {
      for (let i = 0; i < files.length; i++) {
        const reader = new FileReader();
        reader.onload = (e) => {
          if (e.target) {
            const imageDataURL = e.target.result as string;
            this.selectedImages.push(imageDataURL);
          }
        };
        reader.readAsDataURL(files[i]);
      }
    }

    this.photosChanged.emit(this.selectedImages);
  }
  
  removeImage(image: string): void {
    this.selectedImages = this.selectedImages.filter(img => img !== image);
    this.photosChanged.emit(this.selectedImages);
  }

}
