import {CanActivateFn, Router} from '@angular/router';
import {inject} from '@angular/core';
import { StorageService } from '../services/storage.service';
import {StoreService} from '../services/store.service';
import {catchError, map, of} from 'rxjs';
import {BaseResponse} from '../utils/base-response';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const storage = inject(StorageService);
  const storeService = inject(StoreService);

  if (storage.getToken()) {
    storeService.getCurrentUser().subscribe((user) => {
      if (user) {
        return true;
      }else{
        return storeService.getLoginUser().pipe(
          map((value: BaseResponse) => {
            if (value.code === 200) {
              storeService.setCurrentUser(value.body);
              return true;
            }
            storage.signOut();
            router.navigate(['/login']);
            return false;
          }),
          catchError(() => {
            storage.signOut();
            router.navigate(['/login']);
            return of(false);
          })
        );
      }
    })
  }
  router.navigate(['/login']);
  return false;
};
