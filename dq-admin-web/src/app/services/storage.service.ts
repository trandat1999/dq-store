import { Injectable } from '@angular/core';
import {ACCESS_TOKEN, LANGUAGE, LANGUAGE_EN, REFRESH_TOKEN} from '../utils/const';
import {AuthResponse} from '../models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  constructor() { }
  signOut(): void {
    localStorage.removeItem(ACCESS_TOKEN);
    localStorage.removeItem(REFRESH_TOKEN);
    sessionStorage.removeItem(ACCESS_TOKEN);
    sessionStorage.removeItem(REFRESH_TOKEN);
  }
  public saveToken(authResponse:AuthResponse): void {
    localStorage.setItem(ACCESS_TOKEN,authResponse.accessToken);
    localStorage.setItem(REFRESH_TOKEN,authResponse.refreshToken);
  }
  public saveSessionToken(authResponse:AuthResponse): void {
    sessionStorage.setItem(ACCESS_TOKEN,authResponse.accessToken);
    sessionStorage.setItem(REFRESH_TOKEN,authResponse.refreshToken);
  }
  public getToken(): string | null {
    return sessionStorage.getItem(ACCESS_TOKEN) || localStorage.getItem(ACCESS_TOKEN);
  }

  public getRefreshToken(): string | null {
    return sessionStorage.getItem(REFRESH_TOKEN) || localStorage.getItem(REFRESH_TOKEN);
  }
  public setLanguage(language : string) {
    localStorage.removeItem(LANGUAGE);
    if(language) {
      localStorage.setItem(LANGUAGE,language);
    }else{
      localStorage.setItem(LANGUAGE,LANGUAGE_EN);
    }
  }
  public getLanguage() {
    let lang = localStorage.getItem(LANGUAGE);
    if(lang){
      return lang;
    }else{
      localStorage.setItem(LANGUAGE,LANGUAGE_EN);
      return LANGUAGE_EN;
    }
  }
}
