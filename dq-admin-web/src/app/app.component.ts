import {Component, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {NzIconModule} from 'ng-zorro-antd/icon';
import {NzLayoutModule} from 'ng-zorro-antd/layout';
import {NzMenuModule} from 'ng-zorro-antd/menu';
import {NgxSpinnerComponent} from 'ngx-spinner';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
import {StorageService} from './services/storage.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NzIconModule, NzLayoutModule, NzMenuModule, NgxSpinnerComponent, TranslatePipe],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  constructor(private translate: TranslateService,
              private storeService: StorageService) {
  }
  ngOnInit(): void {
    this.translate.use(this.storeService.getLanguage())
  }
}
