import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Plat } from './plat.model';
import { PlatPopupService } from './plat-popup.service';
import { PlatService } from './plat.service';

@Component({
    selector: 'jhi-plat-delete-dialog',
    templateUrl: './plat-delete-dialog.component.html'
})
export class PlatDeleteDialogComponent {

    plat: Plat;

    constructor(
        private platService: PlatService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.platService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'platListModification',
                content: 'Deleted an plat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-plat-delete-popup',
    template: ''
})
export class PlatDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private platPopupService: PlatPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.platPopupService
                .open(PlatDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
