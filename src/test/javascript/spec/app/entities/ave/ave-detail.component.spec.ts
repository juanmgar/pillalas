/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PillalasAlVueloTestModule } from '../../../test.module';
import { AveDetailComponent } from 'app/entities/ave/ave-detail.component';
import { Ave } from 'app/shared/model/ave.model';

describe('Component Tests', () => {
  describe('Ave Management Detail Component', () => {
    let comp: AveDetailComponent;
    let fixture: ComponentFixture<AveDetailComponent>;
    const route = ({ data: of({ ave: new Ave(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PillalasAlVueloTestModule],
        declarations: [AveDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AveDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AveDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ave).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
