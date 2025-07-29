import {ApplicationConfig, provideZoneChangeDetection, importProvidersFrom, inject, LOCALE_ID} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {icons} from './icons-provider';
import {provideNzIcons} from 'ng-zorro-antd/icon';
import {vi_VN, provideNzI18n, NZ_I18N, en_US} from 'ng-zorro-antd/i18n';
import {registerLocaleData} from '@angular/common';
import vi from '@angular/common/locales/vi';
import {FormsModule} from '@angular/forms';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {HttpClient, provideHttpClient, withInterceptors} from '@angular/common/http';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {NgxSpinnerModule} from 'ngx-spinner';
import {NzConfig, provideNzConfig} from 'ng-zorro-antd/core/config';
import {authInterceptor} from './guards/auth.interceptor';

registerLocaleData(vi);

const httpLoaderFactory: (http: HttpClient) => TranslateHttpLoader = (http: HttpClient) =>
  new TranslateHttpLoader(http, './i18n/', '.json');
const ngZorroConfig: NzConfig = {
  message:{
    nzDuration: 5000,
  }
};
export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({eventCoalescing: true}),
    provideRouter(routes),
    provideNzIcons(icons),
    provideNzI18n(vi_VN),
    provideHttpClient(withInterceptors([authInterceptor])),
    importProvidersFrom(FormsModule),
    provideAnimationsAsync(),
    provideNzConfig(ngZorroConfig),
    {
      provide: NZ_I18N, useFactory: () => {
        const localId = inject(LOCALE_ID);
        switch (localId) {
          case 'en':
            return en_US;
          case 'vi':
            return vi_VN;
          default:
            return vi_VN;
        }
      }
    },
    importProvidersFrom([
      TranslateModule.forRoot({
        loader: {
          provide: TranslateLoader,
          useFactory: httpLoaderFactory,
          deps: [HttpClient]
        }
      }),
      NgxSpinnerModule.forRoot({
        type: "ball-spin-clockwise"
      })
    ])
  ]
};
