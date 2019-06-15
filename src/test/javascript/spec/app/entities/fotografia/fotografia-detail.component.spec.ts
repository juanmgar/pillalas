/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PillalasAlVueloTestModule } from '../../../test.module';
import { FotografiaDetailComponent } from 'app/entities/fotografia/fotografia-detail.component';
import { Fotografia } from 'app/shared/model/fotografia.model';

describe('Component Tests', () => {
  describe('Fotografia Management Detail Component', () => {
    let comp: FotografiaDetailComponent;
    let fixture: ComponentFixture<FotografiaDetailComponent>;
    const route = ({ data: of({ fotografia: new Fotografia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PillalasAlVueloTestModule],
        declarations: [FotografiaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FotografiaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FotografiaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fotografia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
