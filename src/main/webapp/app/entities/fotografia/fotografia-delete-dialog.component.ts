import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFotografia } from 'app/shared/model/fotografia.model';
import { FotografiaService } from './fotografia.service';

@Component({
  selector: 'jhi-fotografia-delete-dialog',
  templateUrl: './fotografia-delete-dialog.component.html'
})
export class FotografiaDeleteDialogComponent {
  fotografia: IFotografia;

  constructor(
    protected fotografiaService: FotografiaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fotografiaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fotografiaListModification',
        content: 'Deleted an fotografia'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-fotografia-delete-popup',
  template: ''
})
export class FotografiaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fotografia }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FotografiaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fotografia = fotografia;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/fotografia', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/fotografia', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
