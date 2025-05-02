import React from 'react';
import { Outlet } from 'react-router-dom';
import ClientHeader from '../components/clientComponents/ClientHeader';
import ClientFooter from '../components/clientComponents/ClientFooter';

export default function ClientLayout() {
  return (
    <div className="">
      <div className="sticky-top bg-white shadow-sm">
        <ClientHeader />
      </div>
      <main className="">
        <Outlet />
      </main>
      <ClientFooter />
    </div>
  );
}
