import { Component } from '@angular/core';
import {NzContentComponent, NzHeaderComponent, NzLayoutComponent, NzSiderComponent} from 'ng-zorro-antd/layout';
import {NzMenuDirective, NzMenuItemComponent, NzSubMenuComponent} from 'ng-zorro-antd/menu';
import {AsyncPipe, NgForOf, NgIf, NgOptimizedImage} from '@angular/common';
import {navigation, NavigationItem} from '../../../utils/navigation.model';
import {Router, RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {NzIconDirective} from 'ng-zorro-antd/icon';
import {TranslatePipe, TranslateService} from '@ngx-translate/core';
import {NzOptionComponent, NzSelectComponent} from 'ng-zorro-antd/select';
import {FormsModule} from '@angular/forms';
import {NzAvatarComponent} from 'ng-zorro-antd/avatar';
import {NzDropDownDirective, NzDropdownMenuComponent} from 'ng-zorro-antd/dropdown';
import {BreadcrumbComponent} from "../../commons/breadcrumb/breadcrumb.component";
import {BehaviorSubject} from "rxjs";
import {StorageService} from "../../../services/storage.service";
import {RootService} from "../../../services/root.service";
import {SignalService} from "../../../services/signal.service";
import {AuthService} from "../../auth/auth.service";
import {StoreService} from "../../../services/store.service";

@Component({
  selector: 'dq-main-layout',
  imports: [
    NzLayoutComponent,
    NzSiderComponent,
    NzMenuDirective,
    NgForOf,
    NzMenuItemComponent,
    RouterLink,
    NzIconDirective,
    NgIf,
    NgOptimizedImage,
    NzSubMenuComponent,
    NzIconDirective,
    NzIconDirective,
    TranslatePipe,
    RouterLinkActive,
    NzHeaderComponent,
    NzSelectComponent,
    NzOptionComponent,
    FormsModule,
    NzAvatarComponent,
    NzDropDownDirective,
    NzDropdownMenuComponent,
    BreadcrumbComponent,
    NzContentComponent,
    RouterOutlet,
    AsyncPipe
  ],
  templateUrl: './main-layout.component.html',
  styleUrl: './main-layout.component.css'
})
export class MainLayoutComponent {
  isCollapsed = false;
  currentLanguage = "en";
  currentUser: any;
  navigation = navigation;
  constructor(private authService: AuthService,
              private router: Router,
              private translate: TranslateService,
              private signalService: SignalService,
              private rootService: RootService,
              private store: StoreService,
              private storage: StorageService) {
    this.currentLanguage = this.storage.getLanguage();
    this.store.getCurrentUser().subscribe(user => {
      this.currentUser = user;
    });
    this.signalService.subscribeToSignal().subscribe(signal => {
      if(signal.type === 'navCollapsed'){
        this.isCollapsed = signal.value || false;
      }
    });
  }
  logout(): void {
    this.authService.logout().subscribe(data => {
    });
    this.storage.signOut();
    this.router.navigate(['/login']);
    this.store.setCurrentUser(null);
  }

  changeLanguage(lang: string) {
    this.destroyAndReload();
    this.rootService.changeLanguage(lang);
  }

  translateFn = (key: string) => {
    if (key) {
      return this.translate.instant(key)
    } else {
      return "";
    }
  }
  isVisible$ = new BehaviorSubject(true);

  destroyAndReload() {
    this.isVisible$.next(false);
    setTimeout(() => {
      this.isVisible$.next(true);
    }, 1);
  }
  isOpen(item : NavigationItem): boolean {
    if(item.children){
      let currentUrl = this.router.url;
      for(let sub of item.children){
        if(sub.link && currentUrl.startsWith(sub.link)){
          return true;
        }
      }
    }
    return false;
  }
}
