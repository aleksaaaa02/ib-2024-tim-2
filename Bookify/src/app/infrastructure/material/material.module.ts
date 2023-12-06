import {NgModule} from "@angular/core";
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatDialogModule} from '@angular/material/dialog';

@NgModule({
  declarations: [],
  imports: [
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatCheckboxModule,
    MatDialogModule
  ],
  exports: [
    MatToolbarModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatCheckboxModule,
    MatDialogModule
  ]
})

export class MaterialModule { }
