import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PillalasAlVueloSharedModule } from 'app/shared';
import {
  AveComponent,
  AveDetailComponent,
  AveUpdateComponent,
  AveDeletePopupComponent,
  AveDeleteDialogComponent,
  aveRoute,
  avePopupRoute
} from './';

const ENTITY_STATES = [...aveRoute, ...avePopupRoute];

@NgModule({
  imports: [PillalasAlVueloSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [AveComponent, AveDetailComponent, AveUpdateComponent, AveDeleteDialogComponent, AveDeletePopupComponent],
  entryComponents: [AveComponent, AveUpdateComponent, AveDeleteDialogComponent, AveDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PillalasAlVueloAveModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
