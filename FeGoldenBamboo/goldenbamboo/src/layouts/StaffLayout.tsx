import React from 'react'
import { Outlet } from 'react-router-dom'
import HeaderStaff from '../components/staffComponents/HeaderStaff.tsx'
import OrderStaff from '../components/staffComponents/OrderStaff.js'
export default function StaffLayout() {
  return (
    <div>
      <div><HeaderStaff/></div>
      <div><OrderStaff/></div>
      <div><Outlet/></div>
    </div>
  )
}
