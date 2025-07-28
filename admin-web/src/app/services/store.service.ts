import { Injectable } from '@angular/core';
import {BehaviorSubject, Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StoreService {
  private currentUser = new BehaviorSubject<any>({});

  public setCurrentUser(value: any) {
    this.currentUser.next(value);
  }
  public getCurrentUser() {
    return this.currentUser.asObservable();
  }

  constructor() { }
}
